package com.kms.smartcart_backend.service.impl;

import com.kms.smartcart_backend.repository.CheckitemRepository;
import com.kms.smartcart_backend.service.CheckitemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckitemServiceImpl implements CheckitemService {

    private final CheckitemRepository checkitemRepository;

}
