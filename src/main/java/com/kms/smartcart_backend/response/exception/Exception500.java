package com.kms.smartcart_backend.response.exception;

import com.kms.smartcart_backend.response.ResponseCode;
import lombok.Getter;

@Getter
public class Exception500 extends CustomException {

    public Exception500(ResponseCode errorResponseCode, String message) {
        super(errorResponseCode, message);
    }


    public static class ExternalServer extends Exception500 {
        public ExternalServer(String message) {  // message값 = 외부 API 종류 기재할것.
            super(ResponseCode.EXTERNAL_SERVER_ERROR, message);
        }
    }

    public static class AwsS3Server extends Exception500 {
        public AwsS3Server(String message) {
            super(ResponseCode.AWS_S3_ERROR, message);
        }
    }
}