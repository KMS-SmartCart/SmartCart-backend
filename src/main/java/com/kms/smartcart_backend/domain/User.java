package com.kms.smartcart_backend.domain;

import com.kms.smartcart_backend.domain.enums.Role;
import com.kms.smartcart_backend.domain.enums.SocialType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor

@Table(name ="user")
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    private String nickname;

    @Column(name = "saved_money")
    private Integer savedMoney;  // 절약한 합산 금액

    @Column(name = "social_id")
    private String socialId;  // 소셜 식별값

    @Enumerated(EnumType.STRING)
    private SocialType socialType;  // 소셜 종류

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToMany(mappedBy = "user")  // User-Checkitem 양방향매핑 (읽기 전용 필드)
    private List<Checkitem> checkitemList = new ArrayList<>();  // 체크리스트

    @OneToMany(mappedBy = "user")  // User-Product 양방향매핑 (읽기 전용 필드)
    private List<Product> productList = new ArrayList<>();  // 장바구니 상품리스트 (온라인 + 오프라인)


    @Builder(builderClassName = "UserSaveBuilder", builderMethodName = "UserSaveBuilder")
    public User(String email, String nickname, String socialId, SocialType socialType) {
        // 이 빌더는 사용자 회원가입때만 사용할 용도 (refreshToken=null로 저장됨.)
        this.email = email;
        this.nickname = nickname;
        this.savedMoney = 0;
        this.socialId = socialId;
        this.socialType = socialType;
        this.role = Role.ROLE_USER;
    }


    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void addSavedMoney(Integer savedMoney) {
        this.savedMoney += savedMoney;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
