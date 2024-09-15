package com.kms.smartcart_backend.dto;

import lombok.*;

public class ExternalDto {

    // ======== < Request DTO > ======== //

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatgptImageProcessingRequest {

        private String productName;
        private Integer price;
        private String amount;
    }

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
    public static class ChatgptImageProcessingResponse {

        private String productName;
        private Integer price;
        private String amount;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverShoppingResponse {

        private String link;
        private Integer price;
        private String mallName;

        @Setter
        private String productName;  // ChatGPT의 파싱으로 보다 간소화한 상품명
        @Setter
        private String amount;  // ChatGPT의 파싱으로 얻은 용량
        @Setter
        private String printName;  // 프론트엔드에서 사용자에게 보여줄 이름 문자열 (상품명 + 용량)
    }
}
