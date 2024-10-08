package com.kms.smartcart_backend.controller;

import com.kms.smartcart_backend.dto.UserDto;
import com.kms.smartcart_backend.response.ResponseCode;
import com.kms.smartcart_backend.response.ResponseData;
import com.kms.smartcart_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping  // 기본 URI path
    @Operation(summary = "내정보 Page - 회원 프로필 조회 [JWT O]")
    public ResponseEntity<ResponseData<UserDto.Response>> findUserProfile() {
        UserDto.Response userResponseDto = userService.findUserProfile();
        return ResponseData.toResponseEntity(ResponseCode.READ_USER, userResponseDto);
    }

    @PutMapping  // 기본 URI path
    @Operation(summary = "내정보 Page - 회원 닉네임 변경 [JWT O]")
    public ResponseEntity<ResponseData> updateUser(@RequestBody UserDto.UpdateRequest updateRequestDto) {
        userService.updateUser(updateRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_USER);
    }
}
