package com.kms.smartcart_backend.response.exception;

import com.kms.smartcart_backend.response.ResponseCode;
import lombok.Getter;

@Getter
public class Exception400 extends CustomException {

    public Exception400(ResponseCode errorResponseCode, String message) {
        super(errorResponseCode, message);
    }


    public static class UserBadRequest extends Exception400 {
        public UserBadRequest(String message) {
            super(ResponseCode.BAD_REQUEST_USER, message);
        }
    }

    public static class CheckitemBadRequest extends Exception400 {
        public CheckitemBadRequest(String message) {
            super(ResponseCode.BAD_REQUEST_CHECKITEM, message);
        }
    }

    public static class ProductBadRequest extends Exception400 {
        public ProductBadRequest(String message) {
            super(ResponseCode.BAD_REQUEST_PRODUCT, message);
        }
    }

    public static class TokenBadRequest extends Exception400 {
        public TokenBadRequest(String message) {
            super(ResponseCode.BAD_REQUEST_TOKEN, message);
        }
    }
}