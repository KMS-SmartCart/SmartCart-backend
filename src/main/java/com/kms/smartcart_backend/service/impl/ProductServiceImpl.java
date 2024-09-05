package com.kms.smartcart_backend.service.impl;

import com.kms.smartcart_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {

    private final ProductRepository productRepository;

}
