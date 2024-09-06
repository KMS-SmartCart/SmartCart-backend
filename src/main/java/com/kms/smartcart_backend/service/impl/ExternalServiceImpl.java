package com.kms.smartcart_backend.service.impl;

import com.kms.smartcart_backend.external.NaverShoppingClient;
import com.kms.smartcart_backend.service.ExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExternalServiceImpl implements ExternalService {

    @Value("${naver.clientid}")
    private String naverClientId;
    @Value("${naver.clientsecret}")
    private String naverClientSecret;

    private static final Integer naverDisplay = 3;
    private static final String naverSort = "asc";

    private final NaverShoppingClient naverShoppingClient;


    @Transactional
    @Override
    public void getLowPriceProducts(String query) {
        ResponseEntity<String> response = naverShoppingClient.getLowPriceProducts(
                naverClientId, naverClientSecret, query, naverDisplay, naverSort);
        String jsonResponse = response.getBody();
        System.out.println(jsonResponse);
    }
}
