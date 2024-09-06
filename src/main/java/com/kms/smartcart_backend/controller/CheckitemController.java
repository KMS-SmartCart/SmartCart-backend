package com.kms.smartcart_backend.controller;

import com.kms.smartcart_backend.dto.CheckitemDto;
import com.kms.smartcart_backend.response.ResponseCode;
import com.kms.smartcart_backend.response.ResponseData;
import com.kms.smartcart_backend.service.CheckitemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Checkitem")
@RestController
@RequiredArgsConstructor
@RequestMapping("/checkitem")
public class CheckitemController {

    private final CheckitemService checkitemService;


    @PutMapping  // 기본 URI path
    @Operation(summary = "메인 Page - 체크리스트 항목이름 변경 or 체크여부 변경 [JWT O]",
            description = """
                - <strong>항목이름 변경</strong> : beforeName = 기존 항목이름 , afterName = 변경할 항목이름 , checked = null
                - <strong>체크여부 변경</strong> : beforeName = 기존 항목이름 , afterName = null , checked = 변경할 체크여부 (0 or 1)
                - <strong>ERROR 1</strong> : beforeName = null
                - <strong>ERROR 2</strong> : afterName = null , checked = null
                - <strong>ERROR 3</strong> : afterName != null , checked != null
                - <strong>ERROR 4</strong> : checked = 0 or 1 아닌 다른 숫자의 경우  \n\n<strong>!!! 주의사항</strong> : 체크리스트 항목이름 변경시, 기존 체크리스트에 이미 동일한 항목이름이 있다면 수정 불가능하도록 프론트엔드에서 막을 것 <strong>!!!</strong>
                """)
    public ResponseEntity<ResponseData> updateCheckitem(@RequestBody CheckitemDto.UpdateRequest updateRequestDto) {
        checkitemService.updateCheckitem(updateRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_CHECKITEM);
    }

    @DeleteMapping  // 기본 URI path
    @Operation(summary = "메인 Page - 체크리스트 항목 삭제 [JWT O]")
    public ResponseEntity<ResponseData> deleteCheckitem(@RequestBody CheckitemDto.DeleteRequest deleteRequestDto) {
        checkitemService.deleteCheckitem(deleteRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.DELETE_CHECKITEM);
    }
}
