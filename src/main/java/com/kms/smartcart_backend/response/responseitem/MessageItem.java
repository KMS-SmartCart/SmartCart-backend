package com.kms.smartcart_backend.response.responseitem;

public class MessageItem {

    // < User >
    public static final String CREATED_USER = "SUCCESS - 회원 가입 성공";
    public static final String READ_USER = "SUCCESS - 회원 정보 조회 성공";
    public static final String UPDATE_USER = "SUCCESS - 회원 정보 수정 성공";
    public static final String DELETE_USER = "SUCCESS - 회원 탈퇴 성공";

    public static final String NOT_FOUND_USER = "ERROR - 존재하지 않는 회원 조회 에러";
    public static final String BAD_REQUEST_USER = "ERROR - 잘못된 회원 요청 에러";

    // < Checkitem >
    public static final String CREATED_CHECKITEM = "SUCCESS - 체크리스트 항목 생성 성공";
    public static final String READ_CHECKITEM = "SUCCESS - 체크리스트 항목 조회 성공";
    public static final String UPDATE_CHECKITEM = "SUCCESS - 체크리스트 항목 수정 성공";
    public static final String DELETE_CHECKITEM = "SUCCESS - 체크리스트 항목 삭제 성공";

    public static final String NOT_FOUND_CHECKITEM = "ERROR - 존재하지 않는 체크리스트 항목 조회 에러";
    public static final String BAD_REQUEST_CHECKITEM = "ERROR - 잘못된 체크리스트 항목 요청 에러";

    // < Product >
    public static final String CREATED_PRODUCT = "SUCCESS - 장바구니 상품 생성 성공";
    public static final String READ_PRODUCT = "SUCCESS - 장바구니 상품 조회 성공";
    public static final String UPDATE_PRODUCT = "SUCCESS - 장바구니 상품 수정 성공";
    public static final String DELETE_PRODUCT = "SUCCESS - 장바구니 상품 삭제 성공";

    public static final String NOT_FOUND_PRODUCT = "ERROR - 존재하지 않는 장바구니 상품 조회 에러";
    public static final String BAD_REQUEST_PRODUCT = "ERROR - 잘못된 장바구니 상품 요청 에러";

    // < Auth >
    public static final String PREVENT_GET_ERROR = "Status 204 - 리소스 및 리다이렉트 GET호출 에러 방지";

    public static final String UNAUTHORIZED = "ERROR - Unauthorized 에러";
    public static final String FORBIDDEN = "ERROR - Forbidden 에러";

    // < Token >
    public static final String REISSUE_SUCCESS = "SUCCESS - JWT Access 토큰 재발급 성공";

    public static final String TOKEN_EXPIRED = "ERROR - JWT 토큰 만료 에러";
    public static final String TOKEN_ERROR = "ERROR - 잘못된 JWT 토큰 에러";
    public static final String BAD_REQUEST_TOKEN = "ERROR - 잘못된 토큰 요청 에러";

    // < Etc >
    public static final String HEALTHY_SUCCESS = "SUCCESS - Health check 성공";
    public static final String TEST_SUCCESS = "SUCCESS - Test 성공";  // Test 임시 용도

    public static final String anonymousUser_ERROR = "ERROR - anonymousUser 에러";  // 시큐리티 헤더의 로그인 정보가 없을때 값을 조회하면 발생.
    public static final String INTERNAL_SERVER_ERROR = "ERROR - 서버 내부 에러";
}
