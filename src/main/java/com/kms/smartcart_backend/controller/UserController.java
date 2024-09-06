package com.kms.smartcart_backend.controller;

import com.kms.smartcart_backend.dto.UserDto;
import com.kms.smartcart_backend.response.ResponseCode;
import com.kms.smartcart_backend.response.ResponseData;
import com.kms.smartcart_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/users")
    @Operation(summary = "내정보 Page - 회원 닉네임 변경 or 아낀 총 금액 추가 [JWT O]",
            description = """
                - <strong>회원 닉네임 변경</strong> : nickname = 변경할 닉네임 , savedMoney = null
                - <strong>아낀 총 금액 추가</strong> : nickname = null , savedMoney = 새롭게 아낀 금액
                - <strong>ERROR 1</strong> : nickname = 변경할 닉네임 , savedMoney = 새롭게 아낀 금액
                - <strong>ERROR 2</strong> : nickname = null , savedMoney = null
                """)
    public ResponseEntity<ResponseData> createRoom(@RequestBody UserDto.UpdateRequest updateRequestDto) {
        userService.updateUser(updateRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_USER);
    }
}
