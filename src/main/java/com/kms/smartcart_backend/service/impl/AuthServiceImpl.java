package com.kms.smartcart_backend.service.impl;

import com.kms.smartcart_backend.domain.Checkitem;
import com.kms.smartcart_backend.domain.Product;
import com.kms.smartcart_backend.domain.User;
import com.kms.smartcart_backend.domain.enums.Role;
import com.kms.smartcart_backend.dto.AuthDto;
import com.kms.smartcart_backend.repository.CheckitemBatchRepository;
import com.kms.smartcart_backend.repository.ProductBatchRepository;
import com.kms.smartcart_backend.repository.UserRepository;
import com.kms.smartcart_backend.response.exception.Exception400;
import com.kms.smartcart_backend.security.jwt.TokenProvider;
import com.kms.smartcart_backend.service.AuthService;
import com.kms.smartcart_backend.service.UserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CheckitemBatchRepository checkitemBatchRepository;
    private final ProductBatchRepository productBatchRepository;
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

    @Transactional
    @Override
    public void withdrawal() {
        User user = userService.findLoginUser();
        List<Checkitem> checkitemList = user.getCheckitemList();
        List<Product> productList = user.getProductList();

        // 자식 엔티티인 Checkitem 및 Product 먼저 삭제
        checkitemBatchRepository.batchDelete(checkitemList);  // JDBC의 batch delete를 활용하여, 대용량 Batch 삭제 처리. (DB 여러번 접근 방지 & 성능 향상)
        productBatchRepository.batchDelete(productList);  // JDBC의 batch delete를 활용하여, 대용량 Batch 삭제 처리. (DB 여러번 접근 방지 & 성능 향상)

        // 부모 엔티티인 User 삭제
        userRepository.delete(user);
    }

    @Transactional
    @Override
    public AuthDto.TokenResponse reissue(AuthDto.ReissueRequest reissueRequestDto) {  // Refresh Token으로 Access Token 재발급 메소드

        // RequestDto로 전달받은 Token값들
        String accessToken = reissueRequestDto.getAccessToken();
        String refreshToken = reissueRequestDto.getRefreshToken();

        // Refresh Token 유효성 검사
        if(tokenProvider.validateToken(refreshToken) == false) {
            throw new JwtException("입력한 Refresh Token은 잘못된 토큰입니다.");
        }

        // Access Token에서 userId 가져오기
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        Long userId = Long.valueOf(authentication.getName());

        // userId로 사용자 검색 & 해당 사용자의 role 가져오기
        User user = userService.findUser(userId);
        Role role = user.getRole();
        String dbRefreshToken = user.getRefreshToken();

        // DB의 사용자 Refresh Token 값과, 전달받은 Refresh Token의 불일치 여부 검사
        if(dbRefreshToken == null || !(dbRefreshToken.equals(refreshToken))) {
            throw new Exception400.TokenBadRequest("Refresh Token = " + refreshToken);
        }

        AuthDto.TokenResponse tokenResponseDto = tokenProvider.generateAccessTokenByRefreshToken(userId, role, refreshToken);
        return tokenResponseDto;
    }
}
