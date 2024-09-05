package com.kms.smartcart_backend.response.exception;

import com.kms.smartcart_backend.response.ResponseCode;
import lombok.Getter;

@Getter
public class Exception500 extends CustomException {

    public Exception500(ResponseCode errorResponseCode) {
        super(errorResponseCode, null);
    }

}