package com.kms.smartcart_backend.service;

import com.kms.smartcart_backend.dto.ExternalDto;

import java.util.List;

public interface ExternalService {
    List<ExternalDto.NaverShoppingResponse> getLowPriceProducts(String query);
}
