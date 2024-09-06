package com.kms.smartcart_backend.controller;

import com.kms.smartcart_backend.dto.ExternalDto;
import com.kms.smartcart_backend.response.ResponseCode;
import com.kms.smartcart_backend.response.ResponseData;
import com.kms.smartcart_backend.service.ExternalService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @Hidden
@Tag(name = "Test")
@RestController
@RequiredArgsConstructor
public class TestController {

    private final ExternalService externalService;


    @GetMapping("/health")
    @Operation(summary = "AWS - 서버 헬스체크 [JWT X]", description = "<strong>프론트엔드 사용 X</strong>")
    public ResponseEntity<ResponseData> healthCheck() {
        return ResponseData.toResponseEntity(ResponseCode.HEALTHY_SUCCESS);
    }

    // @GetMapping({"/", "/login", "/favicon.ico"})
    @GetMapping({"/login", "/favicon.ico"})
    @Operation(summary = "AWS - GET 리소스 및 리다이렉트 에러 방지 [JWT X]", description = "<strong>프론트엔드 사용 X</strong>")  // No static resource 및 프론트엔드의 window.location.href='/login' 호출시 발생 에러 방지.
    public ResponseEntity<ResponseData> preventGetError() {
        return ResponseData.toResponseEntity(ResponseCode.PREVENT_GET_ERROR);
    }


    // ========== Test 메소드 ========== //

//    @GetMapping("/test")
//    @Operation(summary = "Test API [JWT X]", description = "<strong>프론트엔드 사용 X</strong>")
//    public ResponseEntity<ResponseData<List<ExternalDto.NaverShoppingResponse>>> getTestResult() {
//        List<ExternalDto.NaverShoppingResponse> testResult = externalService.getLowPriceProducts("새송이 버섯");
//        return ResponseData.toResponseEntity(ResponseCode.TEST_SUCCESS, testResult);
//    }
}
