package com.kms.smartcart_backend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.smartcart_backend.dto.ExternalDto;
import com.kms.smartcart_backend.external.ChatgptClient;
import com.kms.smartcart_backend.external.NaverShoppingClient;
import com.kms.smartcart_backend.response.exception.Exception500;
import com.kms.smartcart_backend.service.AwsS3Service;
import com.kms.smartcart_backend.service.ExternalService;
import com.kms.smartcart_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExternalServiceImpl implements ExternalService {

    @Value("${chatgpt.apikey}")
    private String apiKey;
    @Value("${naver.clientid}")
    private String naverClientId;
    @Value("${naver.clientsecret}")
    private String naverClientSecret;


    private static final Integer naverDisplay = 50;
    private static final String naverSort = "sim";

    private final UserService userService;
    private final AwsS3Service awsS3Service;
    private final ChatgptClient chatgptClient;
    private final NaverShoppingClient naverShoppingClient;
    private final ObjectMapper objectMapper;


    @Transactional
    @Override
    public ExternalDto.ChatgptImageProcessingResponse getImageInfo(MultipartFile imageFile) throws IOException {
        userService.findLoginUser();  // 로그인 사용자의 DB 존재여부 확인.

        // AWS S3 이미지 업로드 후 url 반환
        String imageFileUrl = awsS3Service.uploadImage(imageFile);

        // 질문 텍스트 전처리
        String question = "해당 사진의 상품명, 용량, 가격을 말해. 대답형식은 '상품명: %s, 용량: %s, 가격: %d' 이렇게 대답해. "
                + "용량 단위는 ml, g, 개 등의 통상적인 단위 종류는 모두 가능하고, 만약 없다면 '없음'으로 채워줘. "
                + "가격은 숫자의 콤마 뗀 상태로 대한민국 원화 기준의 숫자로만 말해.";

        String productName = null;  // 상품명
        String amount = null;  // 용량
        Integer price = null;  // 가격

        int attemptCnt = 0;  // 호출 횟수 카운트
        while(attemptCnt < 20) {
            // ChatGPT API 호출
            String answer = getChatgptAnswer(question, imageFileUrl);

            // 대답 텍스트 파싱
            Pattern pattern = Pattern.compile("상품명: (.*?), 용량: (.*?), 가격: (\\d+)");
            Matcher matcher = pattern.matcher(answer);
            if(matcher.find()) {
                productName = matcher.group(1);
                amount = matcher.group(2);
                price = Integer.parseInt(matcher.group(3));
                if(amount.equals("없음")) amount = "";
                if(productName.equals("없음")) productName = "";
                else if(amount != null) amount = amount.replaceAll("\\s+", "");  // 용량 문자열 내의 모든 공백 제거
                break;  // 정상적으로 문자열 추출이 되었으니, 반복문을 빠져나감.
            }
            // else : 대답 형식이 잘못되어 문자열 추출이 안되었으니, 다시 질문하여 결과 갱신.
            attemptCnt++;
        }
        if(attemptCnt >= 20) throw new Exception500.ExternalServer("ChatGPT API 호출 에러 (호출 제한횟수 초과)");

        ExternalDto.ChatgptImageProcessingResponse chatgptImageProcessingResponseDto = ExternalDto.ChatgptImageProcessingResponse.builder()
                .productName(productName)
                .price(price)
                .amount(amount)
                .build();

        // AWS S3 이미지 삭제
        awsS3Service.deleteImage(imageFileUrl);

        return chatgptImageProcessingResponseDto;
    }

    @Transactional
    @Override
    public List<ExternalDto.NaverShoppingResponse> getLowPriceProducts(String query) {
        userService.findLoginUser();  // 로그인 사용자의 DB 존재여부 확인.

        ResponseEntity<String> response = naverShoppingClient.callNaverShoppingApi(
                naverClientId, naverClientSecret, query, naverDisplay, naverSort);  // 1차적으로는 정확도순으로 50가지를 조회함.
        String jsonResponse = response.getBody();

        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            JsonNode itemsNode = jsonNode.path("items");
            List<ExternalDto.NaverShoppingResponse> naverShoppingResponseDtoList = new ArrayList<>();

            for (JsonNode itemNode : itemsNode) {
                String title = itemNode.path("title").asText().replaceAll("<[^>]*>", "").trim();  // HTML 태그 제거
                String link = itemNode.path("link").asText();
                Integer lprice = itemNode.path("lprice").asInt();
                String mallName = itemNode.path("mallName").asText();
                // String maker = itemNode.path("maker").asText();
                // String brand = itemNode.path("brand").asText();

                ExternalDto.NaverShoppingResponse naverShoppingResponseDto = ExternalDto.NaverShoppingResponse.builder()
                        .productName(title)
                        .link(link)
                        .price(lprice)
                        .mallName(mallName)
                        .build();
                naverShoppingResponseDtoList.add(naverShoppingResponseDto);
            }

            // 가격 기준으로 정렬 (2차적으로는 가격 오름차순으로 3가지만 선별함.)
            List<ExternalDto.NaverShoppingResponse> sortedNaverShoppingResponseDtoList = naverShoppingResponseDtoList.stream()
                    .sorted(Comparator.comparingInt(ExternalDto.NaverShoppingResponse::getPrice))  // stable sort
                    .limit(3)
                    .collect(Collectors.toList());
            return sortedNaverShoppingResponseDtoList;
        } catch (Exception ex) {
            throw new Exception500.ExternalServer("네이버 쇼핑 API 호출 에러 (" + ex.getMessage() + ")");
        }
    }


    // ========== 유틸성 메소드 ========== //

    private String getChatgptAnswer(String question, String imageUrl) {

        // ChatGPT 모델 구성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");

        // 메시지 구성
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", List.of(
                Map.of("type", "text", "text", question),
                Map.of("type", "image_url", "image_url", Map.of("url", imageUrl))
        ));
        requestBody.put("messages", List.of(message));

        // 최대 토큰길이 구성
        requestBody.put("max_tokens", 300);

        // ChatGPT API 호출
        String headerApiKey = "Bearer " + apiKey;
        ResponseEntity<String> response = chatgptClient.callChatgptApiForImageProcessing(headerApiKey, requestBody);
        String jsonResponse = response.getBody();

        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            JsonNode choicesNode = jsonNode.path("choices");
            if(choicesNode.isEmpty()) throw new Exception500.ExternalServer("ChatGPT API 호출 에러 (응답에 선택지가 없음)");
            JsonNode choiceNode = choicesNode.get(0);
            JsonNode messageNode = choiceNode.path("message");
            String content = messageNode.path("content").asText();
            return content;
        } catch (Exception ex) {
            throw new Exception500.ExternalServer("ChatGPT API 호출 에러 (" + ex.getMessage() + ")");
        }
    }
}
