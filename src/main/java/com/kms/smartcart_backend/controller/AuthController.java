package com.kms.smartcart_backend.controller;

import com.kms.smartcart_backend.dto.AuthDto;
import com.kms.smartcart_backend.response.ResponseCode;
import com.kms.smartcart_backend.response.ResponseData;
import com.kms.smartcart_backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @DeleteMapping("/users")
    @Operation(summary = "내정보 Page - 회원 탈퇴 [JWT O]")
    public ResponseEntity<ResponseData> withdrawal() {
        authService.withdrawal();
        return ResponseData.toResponseEntity(ResponseCode.DELETE_USER);
    }

    @PostMapping("/reissue")
    @Operation(summary = "로그인 유지 - JWT Access Token 재발급 [JWT X]")
    public ResponseEntity<ResponseData<AuthDto.TokenResponse>> reissue(@RequestBody AuthDto.ReissueRequest reissueRequestDto) {
        AuthDto.TokenResponse tokenResponseDto = authService.reissue(reissueRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.REISSUE_SUCCESS, tokenResponseDto);
    }
}
