package com.kms.smartcart_backend.service;

import com.kms.smartcart_backend.domain.User;
import com.kms.smartcart_backend.dto.UserDto;

public interface UserService {
    User findUser(Long userId);
    User findLoginUser();
    UserDto.Response findUserProfile();
}
