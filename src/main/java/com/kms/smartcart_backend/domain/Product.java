package com.kms.smartcart_backend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

@Table(name ="product", indexes = {@Index(name = "product_online_idx", columnList = "online")})  // 온라인 및 오프라인 구분 성능향상
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(columnDefinition = "TINYINT(1) default 0", length = 1)
    private Integer online;  // 온라인(1) or 오프라인(0)

    @Column(name = "product_name")
    private String productName;

    private Integer price;

    private String amount;  // 용량 : 개수 or 그람(g) or 리터(ml)

    @ManyToOne(fetch = FetchType.LAZY)  // User-Product 양방향매핑
    @JoinColumn(name = "user_id")
    private User user;


    @Builder(builderClassName = "ProductSaveBuilder", builderMethodName = "ProductSaveBuilder")
    public Product(Integer online, String productName, Integer price, String amount, User user) {
        // 이 빌더는 장바구니 상품등록때만 사용할 용도
        this.online = online;
        this.productName = productName;
        this.price = price;
        this.amount = amount;
        this.user = user;
    }
}
