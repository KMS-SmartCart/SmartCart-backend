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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExternalServiceImpl implements ExternalService {

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

        // chatgptClient.callChatgptApiForImageProcessing(imageFile);
        ExternalDto.ChatgptImageProcessingResponse chatgptImageProcessingResponseDto = null;

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
}
