package com.kms.smartcart_backend.external;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "chatgpt", url = "https://api.openai.com/v1/")
public interface ChatgptClient {

    @Headers("Content-Type: application/json")
    @PostMapping("/chat/completions")
    ResponseEntity<String> callChatgptApiForImageProcessing(
            @RequestHeader("Authorization") String apikey,
            @RequestBody Map<String, Object> requestBody
    );
}
