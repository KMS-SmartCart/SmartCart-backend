package com.kms.smartcart_backend.response;

import com.kms.smartcart_backend.response.responseitem.MessageItem;
import com.kms.smartcart_backend.response.responseitem.StatusItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    // Swagger API 응답값 미리보기 용도
    string(StatusItem.OK, "Swagger API"),

    // ===================== //

    // User 성공 응답
    CREATED_USER(StatusItem.CREATED, MessageItem.CREATED_USER),
    READ_USER(StatusItem.OK, MessageItem.READ_USER),
    UPDATE_USER(StatusItem.NO_CONTENT, MessageItem.UPDATE_USER),
    DELETE_USER(StatusItem.NO_CONTENT, MessageItem.DELETE_USER),

    // User 실패 응답
    NOT_FOUND_USER(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_USER),
    BAD_REQUEST_USER(StatusItem.BAD_REQUEST, MessageItem.BAD_REQUEST_USER),

    // ===================== //

    // Checkitem 성공 응답
    CREATED_CHECKITEM(StatusItem.CREATED, MessageItem.CREATED_CHECKITEM),
    READ_CHECKITEM(StatusItem.OK, MessageItem.READ_CHECKITEM),
    UPDATE_CHECKITEM(StatusItem.NO_CONTENT, MessageItem.UPDATE_CHECKITEM),
    DELETE_CHECKITEM(StatusItem.NO_CONTENT, MessageItem.DELETE_CHECKITEM),

    // Checkitem 실패 응답
    NOT_FOUND_CHECKITEM(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_CHECKITEM),
    BAD_REQUEST_CHECKITEM(StatusItem.BAD_REQUEST, MessageItem.BAD_REQUEST_CHECKITEM),

    // ===================== //

    // Product 성공 응답
    CREATED_PRODUCT(StatusItem.CREATED, MessageItem.CREATED_PRODUCT),
    READ_PRODUCT(StatusItem.OK, MessageItem.READ_PRODUCT),
    UPDATE_PRODUCT(StatusItem.NO_CONTENT, MessageItem.UPDATE_PRODUCT),
    DELETE_PRODUCT(StatusItem.NO_CONTENT, MessageItem.DELETE_PRODUCT),

    // Product 실패 응답
    NOT_FOUND_PRODUCT(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_PRODUCT),
    BAD_REQUEST_PRODUCT(StatusItem.BAD_REQUEST, MessageItem.BAD_REQUEST_PRODUCT),

    // ===================== //

    // Auth 실패 응답
    UNAUTHORIZED_ERROR(StatusItem.UNAUTHORIZED, MessageItem.UNAUTHORIZED),
    FORBIDDEN_ERROR(StatusItem.FORBIDDEN, MessageItem.FORBIDDEN),

    // ===================== //

    // Token 성공 응답
    REISSUE_SUCCESS(StatusItem.OK, MessageItem.REISSUE_SUCCESS),

    // Token 실패 응답
    TOKEN_EXPIRED(StatusItem.UNAUTHORIZED, MessageItem.TOKEN_EXPIRED),
    TOKEN_ERROR(StatusItem.UNAUTHORIZED, MessageItem.TOKEN_ERROR),
    BAD_REQUEST_TOKEN(StatusItem.BAD_REQUEST, MessageItem.BAD_REQUEST_TOKEN),

    // ===================== //

    // 기타 성공 응답
    HEALTHY_SUCCESS(StatusItem.OK, MessageItem.HEALTHY_SUCCESS),
    TEST_SUCCESS(StatusItem.OK, MessageItem.TEST_SUCCESS),
    PREVENT_GET_ERROR(StatusItem.NO_CONTENT, MessageItem.PREVENT_GET_ERROR),

    // 기타 실패 응답
    anonymousUser_ERROR(StatusItem.UNAUTHORIZED, MessageItem.anonymousUser_ERROR),  // 401 Error
    INTERNAL_SERVER_ERROR(StatusItem.INTERNAL_SERVER_ERROR, MessageItem.INTERNAL_SERVER_ERROR),  // 500 Error

    // ===================== //
    ;

    private int httpStatus;
    private String message;
}
