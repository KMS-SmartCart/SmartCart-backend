package com.kms.smartcart_backend.service.impl;

import com.kms.smartcart_backend.domain.User;
import com.kms.smartcart_backend.dto.UserDto;
import com.kms.smartcart_backend.repository.UserRepository;
import com.kms.smartcart_backend.response.exception.Exception404;
import com.kms.smartcart_backend.service.UserService;
import com.kms.smartcart_backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    @Override
    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new Exception404.NoSuchUser(String.format("userId = %d", userId)));
    }

    @Transactional(readOnly = true)
    @Override
    public User findLoginUser() {
        Long loginUserId = SecurityUtil.getCurrentMemberId();
        User loginUser = findUser(loginUserId);
        return loginUser;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto.Response findUserProfile() {
        User user = findLoginUser();
        UserDto.Response userResponseDto = new UserDto.Response(user);
        return userResponseDto;
    }
}
