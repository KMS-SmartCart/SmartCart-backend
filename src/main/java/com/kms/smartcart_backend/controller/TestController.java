package com.kms.smartcart_backend.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @Hidden
@Tag(name = "Test")
@RestController
@RequiredArgsConstructor
public class TestController {

//    @GetMapping("/health")
//    @Operation(summary = "AWS - 서버 헬스체크 [JWT X]")
//    public ResponseEntity<ResponseData> healthCheck() {
//        return ResponseData.toResponseEntity(ResponseCode.HEALTHY_SUCCESS);
//    }
}
