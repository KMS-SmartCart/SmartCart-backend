package com.kms.smartcart_backend.dto;

import com.kms.smartcart_backend.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductDto {

    // ======== < Request DTO > ======== //


    // ======== < Response DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class Response {

        private Long productId;
        private String name;
        private Integer price;
        private String amount;
        private String printName;  // 프론트엔드에서 사용자에게 출력해줄 이름 (상품명 + 용량)

        public Response(Product entity) {
            this.productId = entity.getId();
            this.name = entity.getName();
            this.price = entity.getPrice();
            this.amount = entity.getAmount();
            this.printName = String.format("%s %s", name, amount);
        }
    }
}
