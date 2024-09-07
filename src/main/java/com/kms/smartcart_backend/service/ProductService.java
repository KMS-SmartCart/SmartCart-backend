package com.kms.smartcart_backend.service;

import com.kms.smartcart_backend.dto.ProductDto;

public interface ProductService {
    void saveInBasket(ProductDto.SaveRequest saveRequestDto);
}
