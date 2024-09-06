package com.kms.smartcart_backend.dto;

import com.kms.smartcart_backend.domain.Checkitem;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CheckitemDto {

    // ======== < Request DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {

        private String beforeName;

        private String afterName;
        private Integer checked;
    }

    @Getter
    @NoArgsConstructor
    public static class DeleteRequest {

        private String checkitemName;
    }


    // ======== < Response DTO > ======== //

    @Getter
    @NoArgsConstructor
    public static class Response {

        private Long checkitemId;
        private Integer checked;
        private String checkitemName;

        public Response(Checkitem entity) {
            this.checkitemId = entity.getId();
            this.checked = entity.getChecked();
            this.checkitemName = entity.getCheckitemName();
        }
    }
}
