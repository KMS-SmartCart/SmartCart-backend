package com.kms.smartcart_backend.controller;

import com.kms.smartcart_backend.dto.ExternalDto;
import com.kms.smartcart_backend.response.ResponseCode;
import com.kms.smartcart_backend.response.ResponseData;
import com.kms.smartcart_backend.service.ExternalService;
import com.kms.smartcart_backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Product")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ExternalService externalService;


    @PostMapping(value = "/products/chatgpt/image-processing", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "ChatGPT API - 영상처리 상품 정보 도출 [JWT O]")
    public ResponseEntity<ResponseData<ExternalDto.ChatgptImageProcessingResponse>> getImageInfo(@RequestPart(value="imageFile", required = true) MultipartFile imageFile) {
        ExternalDto.ChatgptImageProcessingResponse ChatgptImageProcessingResponseDto = externalService.getImageInfo(imageFile);
        return ResponseData.toResponseEntity(ResponseCode.CHATGPT_SERVER_SUCCESS, ChatgptImageProcessingResponseDto);
    }

    @PostMapping("/products/naver/lowest-price")
    @Operation(summary = "Naver Shopping API - 최저가 상품 3가지 오름차순 조회 [JWT O]")
    public ResponseEntity<ResponseData<List<ExternalDto.NaverShoppingResponse>>> getLowPriceProducts(@RequestBody ExternalDto.NaverShoppingRequest naverShoppingRequestDto) {
        String searchQuery = String.format("%s %s", naverShoppingRequestDto.getProductName(), naverShoppingRequestDto.getAmount());  // 상품명 + 용량
        List<ExternalDto.NaverShoppingResponse> naverShoppingResponseDtoList = externalService.getLowPriceProducts(searchQuery);
        return ResponseData.toResponseEntity(ResponseCode.NAVER_SERVER_SUCCESS, naverShoppingResponseDtoList);
    }
}
