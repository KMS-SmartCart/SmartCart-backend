package com.kms.smartcart_backend.dto;

import com.kms.smartcart_backend.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductDto {

    // ======== < Request DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class SaveRequest {

        private Integer selectType;  // 오프라인 상품 선택(0) or 온라인 상품 선택(1)
        private Integer savedMoney;  // 아낀 금액 (Swagger API 명세서 설명에 작성하겠음.)

        private String offlineProductName;
        private Integer offlinePrice;
        private String offlineAmount;

        private String onlineProductName;
        private Integer onlinePrice;
        private String onlineAmount;
    }


    // ======== < Response DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class Response {

        private Long productId;
        private Integer isSelect;
        private String productName;
        private Integer price;
        private String amount;
        private String printName;  // 프론트엔드에서 사용자에게 출력해줄 이름 (상품명 + 용량)

        public Response(Product entity) {
            this.productId = entity.getId();
            this.isSelect = entity.getIsSelect();
            this.productName = entity.getProductName();
            this.price = entity.getPrice();
            this.amount = entity.getAmount();
            this.printName = String.format("%s %s", entity.getProductName(), entity.getAmount());
        }
    }
}
