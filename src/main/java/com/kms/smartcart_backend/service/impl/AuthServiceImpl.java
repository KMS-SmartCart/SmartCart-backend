package com.kms.smartcart_backend.service.impl;

import com.kms.smartcart_backend.domain.User;
import com.kms.smartcart_backend.domain.enums.Role;
import com.kms.smartcart_backend.dto.AuthDto;
import com.kms.smartcart_backend.security.jwt.TokenProvider;
import com.kms.smartcart_backend.service.AuthService;
import com.kms.smartcart_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final TokenProvider tokenProvider;


    @Transactional
    @Override
    public AuthDto.TokenResponse oauth2Login(Long userId, Role role) {
        User user = userService.findUser(userId);
        String refreshToken = user.getRefreshToken();
        AuthDto.TokenResponse tokenResponseDto;

        if(refreshToken == null || tokenProvider.validateToken(refreshToken) == false) {  // DB에 Refresh Token이 아직 등록되지 않았거나, 만료 또는 잘못된 토큰인 경우
            tokenResponseDto = tokenProvider.generateTokenDto(userId, role);  // Access Token & Refresh Token 모두를 재발급.
            user.updateRefreshToken(tokenResponseDto.getRefreshToken());  // DB Refresh Token 업데이트.
        }
        else {  // DB에 만료되지않은 정상적인 Refresh Token을 갖고있는 경우
            tokenResponseDto = tokenProvider.generateAccessTokenByRefreshToken(userId, role, refreshToken);  // 오직 Access Token 하나만을 재발급.
        }
        return tokenResponseDto;  // 로그인 절차 완료. JWT 토큰 생성.
    }
}
