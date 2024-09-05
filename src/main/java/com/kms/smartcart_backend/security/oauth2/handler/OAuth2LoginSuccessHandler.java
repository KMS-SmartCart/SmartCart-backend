package com.kms.smartcart_backend.security.oauth2.handler;

import com.kms.smartcart_backend.domain.enums.Role;
import com.kms.smartcart_backend.dto.AuthDto;
import com.kms.smartcart_backend.security.oauth2.CustomOAuth2User;
import com.kms.smartcart_backend.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthService authService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");

        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            Long userId = oAuth2User.getUserId();
            Role role = oAuth2User.getRole();

            AuthDto.TokenResponse tokenResponseDto = authService.oauth2Login(userId, role);  // Access & Refresh 토큰 발행. (Refresh Token 재발급은 조건적으로.)
            String accessToken = tokenResponseDto.getAccessToken();
            log.info("발급된 Access Token : {}", accessToken);
            String refreshToken = tokenResponseDto.getRefreshToken();
            log.info("발급된 Refresh Token : {}", refreshToken);

            String redirectUrl = makeRedirectUrl(tokenResponseDto);
            log.info("JWT 헤더를 가진채로, 메인 페이지로 리다이렉트 시킵니다.");  // 리다이렉트(프론트엔드 url)는 백엔드에서 시키고, 헤더에 jwt 다는건 프론트엔드에서.

            getRedirectStrategy().sendRedirect(request, response, redirectUrl);

        } catch (Exception e) {
            throw e;
        }
    }

    public String makeRedirectUrl(AuthDto.TokenResponse tokenResponseDto) {
        String frontendUrl = "http://localhost:3000";  // "https://www.smartcart.kr"
        frontendUrl += "/main";

        String redirectUrl = UriComponentsBuilder.fromUriString(frontendUrl)  // 프론트엔드 url
                // .queryParam("grantType", tokenResponseDto.getGrantType())
                .queryParam("accessToken", tokenResponseDto.getAccessToken())
                // .queryParam("accessTokenExpiresIn", tokenResponseDto.getAccessTokenExpiresIn())
                .queryParam("refreshToken", tokenResponseDto.getRefreshToken())
                .build().toUriString();
        return redirectUrl;
    }
}