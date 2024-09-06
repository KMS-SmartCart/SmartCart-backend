package com.kms.smartcart_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ExternalDto {

    // ======== < Request DTO > ======== //


    // ======== < Response DTO > ======== //

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverShoppingResponse {

        private String title;
        private String link;
        private Integer price;
        private String mallName;
    }
}
