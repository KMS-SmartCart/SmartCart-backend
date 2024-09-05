package com.kms.smartcart_backend.domain;

import com.kms.smartcart_backend.domain.enums.Role;
import com.kms.smartcart_backend.domain.enums.SocialType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor

@Table(name ="user")
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

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

    // 여기는 이외의 타매핑 컬럼 작성할것.


    @Builder(builderClassName = "UserSaveBuilder", builderMethodName = "UserSaveBuilder")
    public User(String nickname, String socialId, SocialType socialType) {
        // 이 빌더는 사용자 회원가입때만 사용할 용도 (refreshToken=null로 저장됨.)
        this.nickname = nickname;
        this.savedMoney = 0;
        this.socialId = socialId;
        this.socialType = socialType;
        this.role = Role.ROLE_USER;
    }


    public void addSavedMoney(Integer addSavedMoney) {
        this.savedMoney += addSavedMoney;
    }
}
