package com.kms.smartcart_backend.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "navershopping", url = "https://openapi.naver.com/v1/search/shop.json/")
public interface NaverShoppingClient {

    @GetMapping  // 기본 URI path
    ResponseEntity<String> getLowPriceProducts(
            @RequestHeader("X-Naver-Client-Id") String clientId,
            @RequestHeader("X-Naver-Client-Secret") String clientSecret,
            @RequestParam("query") String query,  // 검색어 (UTF-8로 인코딩 해야함. 근데 추가적으로는 필요없는듯하다.)
            @RequestParam("display") Integer display,  // 한 번에 표시할 검색 결과 개수 => 3
            @RequestParam("sort") String sort  // 검색 결과 정렬 방법 (가격순으로 오름차순 정렬 할것.) => asc
    );
}
