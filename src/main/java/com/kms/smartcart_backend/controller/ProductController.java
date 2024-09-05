package com.kms.smartcart_backend.controller;

import com.kms.smartcart_backend.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

}
