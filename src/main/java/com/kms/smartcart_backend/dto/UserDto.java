package com.kms.smartcart_backend.dto;

import com.kms.smartcart_backend.domain.User;
import com.kms.smartcart_backend.domain.enums.SocialType;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

    // ======== < Request DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {

        // 밑의 필드 둘중 하나는 반드시 null값임.
        private String nickname;
        private Integer savedMoney;
    }


    // ======== < Response DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class Response {

        private Long userId;
        private String nickname;
        private Integer savedMoney;
        private SocialType socialType;

        public Response(User entity) {
            this.userId = entity.getId();
            this.nickname = entity.getNickname();
            this.savedMoney = entity.getSavedMoney();
            this.socialType = entity.getSocialType();
        }
    }
}
