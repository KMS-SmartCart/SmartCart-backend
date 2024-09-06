package com.kms.smartcart_backend.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "chatgpt", url = "*")
public interface ChatgptClient {

    @PostMapping("*")
    ResponseEntity<String> callChatgptApiForImageProcessing(

    );
}
