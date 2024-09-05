package com.kms.smartcart_backend.service.impl;

import com.kms.smartcart_backend.repository.ProductRepository;
import com.kms.smartcart_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

}
