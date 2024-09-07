package com.kms.smartcart_backend.controller;

import com.kms.smartcart_backend.dto.ExternalDto;
import com.kms.smartcart_backend.dto.ProductDto;
import com.kms.smartcart_backend.response.ResponseCode;
import com.kms.smartcart_backend.response.ResponseData;
import com.kms.smartcart_backend.service.ExternalService;
import com.kms.smartcart_backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Product")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ExternalService externalService;


    @GetMapping  // 기본 URI path
    @Operation(summary = "장바구니 Page - 장바구니 조회 [JWT O]",
            description = """
                    <strong>< 응답 필드 의미(예시) ></strong>
                    - <strong>printName</strong> : 사용자에게 보여줄 이름 문자열 (상품명+용량 = "새송이버섯 300g")
                    - <strong>printPrice</strong> : 사용자에게 보여줄 가격 문자열 (가격+원 = "1300원")
                    """)
    public ResponseEntity<ResponseData<ProductDto.BasketResponse>> findBasket() {
        ProductDto.BasketResponse basketResponseDto = productService.findBasket();
        return ResponseData.toResponseEntity(ResponseCode.READ_PRODUCT, basketResponseDto);
    }

    @PostMapping(value = "/image-processing", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "스마트렌즈 과정 1 - ChatGPT API : 영상처리 상품 정보 도출 [JWT O]")
    public ResponseEntity<ResponseData<ExternalDto.ChatgptImageProcessingResponse>> getImageInfo(@RequestPart(value="imageFile", required = true) MultipartFile imageFile) throws IOException {
        ExternalDto.ChatgptImageProcessingResponse ChatgptImageProcessingResponseDto = externalService.getImageInfo(imageFile);
        return ResponseData.toResponseEntity(ResponseCode.CHATGPT_SERVER_SUCCESS, ChatgptImageProcessingResponseDto);
    }

    @PostMapping("/lowest-price")
    @Operation(summary = "스마트렌즈 과정 2 - Naver Shopping API : 최저가 온라인 상품 3가지 오름차순 조회 [JWT O]")
    public ResponseEntity<ResponseData<List<ExternalDto.NaverShoppingResponse>>> getLowPriceProducts(@RequestBody ExternalDto.NaverShoppingRequest naverShoppingRequestDto) {
        String searchQuery = String.format("%s %s", naverShoppingRequestDto.getProductName(), naverShoppingRequestDto.getAmount());  // 상품명 + 용량
        List<ExternalDto.NaverShoppingResponse> naverShoppingResponseDtoList = externalService.getLowPriceProducts(searchQuery);
        return ResponseData.toResponseEntity(ResponseCode.NAVER_SERVER_SUCCESS, naverShoppingResponseDtoList);
    }

    @PostMapping  // 기본 URI path
    @Operation(summary = "스마트렌즈 과정 3 - 온라인 및 오프라인 상품 장바구니에 담기 [JWT O]",
            description = """
                    <strong>< 요청 필드 의미(값) ></strong>
                    - <strong>selectType</strong> : 오프라인 상품 선택 or 온라인 상품 선택 (0 or 1)
                    - <strong>savedMoney</strong> : 선택 상품에 대한 아낀 금액 (밑에 명시한 계산법의 결과값)  \n
                    <strong>< 경우 및 계산법 ></strong>
                    - <strong>오프라인 선택 & 오프라인 저렴</strong> : 온라인 장바구니 = 온라인 3가지중 최저가 상품 , 오프라인 장바구니 = 선택한 오프라인 상품 , savedMoney = 두 상품의 가격 차이
                    - <strong>오프라인 선택 & 오프라인 비쌈</strong> : 온라인 장바구니 = 온라인 3가지중 최저가 상품 , 오프라인 장바구니 = 선택한 오프라인 상품 , savedMoney = 0
                    - <strong>온라인 선택 & 온라인 저렴</strong> : 온라인 장바구니 = 선택한 온라인 상품 , 오프라인 장바구니 = 기존 오프라인 상품 , savedMoney = 두 상품의 가격 차이
                    - <strong>온라인 선택 & 온라인 비쌈</strong> : 온라인 장바구니 = 선택한 온라인 상품 , 오프라인 장바구니 = 기존 오프라인 상품 , savedMoney = 0  \n\n<strong>!!! 주의사항</strong> : savedMoney 값은 프론트엔드에서 계산할 것 <strong>!!!</strong>
                    """)
    public ResponseEntity<ResponseData> saveInBasket(@RequestBody ProductDto.SaveRequest saveRequestDto) {
        productService.saveInBasket(saveRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_PRODUCT);
    }
}
