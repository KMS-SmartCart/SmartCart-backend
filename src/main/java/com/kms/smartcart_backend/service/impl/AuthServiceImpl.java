package com.kms.smartcart_backend.service.impl;

import com.kms.smartcart_backend.repository.UserRepository;
import com.kms.smartcart_backend.service.AuthService;
import com.kms.smartcart_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

}
