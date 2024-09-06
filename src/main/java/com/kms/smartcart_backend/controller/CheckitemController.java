package com.kms.smartcart_backend.controller;

import com.kms.smartcart_backend.service.CheckitemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Checkitem")
@RestController
@RequiredArgsConstructor
public class CheckitemController {

    private final CheckitemService checkitemService;

}
