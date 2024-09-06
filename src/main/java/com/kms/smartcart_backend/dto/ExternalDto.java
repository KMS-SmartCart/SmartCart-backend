package com.kms.smartcart_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ExternalDto {

    // ======== < Request DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class NaverShoppingRequest {

        private String productName;
        private String amount;
    }


    // ======== < Response DTO > ======== //

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverShoppingResponse {

        private String productName;
        private String link;
        private Integer price;
        private String mallName;
    }
}
