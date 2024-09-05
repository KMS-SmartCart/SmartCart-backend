package com.kms.smartcart_backend.service;

import com.kms.smartcart_backend.domain.enums.Role;
import com.kms.smartcart_backend.dto.AuthDto;

public interface AuthService {
    AuthDto.TokenResponse oauth2Login(Long userId, Role role);
    void withdrawal();
    AuthDto.TokenResponse reissue(AuthDto.ReissueRequest reissueRequestDto);
}
