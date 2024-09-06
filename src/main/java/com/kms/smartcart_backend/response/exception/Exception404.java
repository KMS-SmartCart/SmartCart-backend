package com.kms.smartcart_backend.response.exception;

import com.kms.smartcart_backend.response.ResponseCode;
import lombok.Getter;

@Getter
public class Exception404 extends CustomException {

    public Exception404(ResponseCode errorResponseCode, String message) {
        super(errorResponseCode, message);
    }


    public static class NoSuchUser extends Exception404 {
        public NoSuchUser(String message) {
            super(ResponseCode.NOT_FOUND_USER, message);
        }
    }

    public static class NoSuchCheckitem extends Exception404 {
        public NoSuchCheckitem(String message) {
            super(ResponseCode.NOT_FOUND_CHECKITEM, message);
        }
    }

    public static class NoSuchProduct extends Exception404 {
        public NoSuchProduct(String message) {
            super(ResponseCode.NOT_FOUND_PRODUCT, message);
        }
    }
}