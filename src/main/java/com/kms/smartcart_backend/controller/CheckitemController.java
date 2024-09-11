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

import java.util.List;

@Tag(name = "Checkitem")
@RestController
@RequiredArgsConstructor
@RequestMapping("/checkitems")
public class CheckitemController {

    private final CheckitemService checkitemService;


    @GetMapping  // 기본 URI path
    @Operation(summary = "메인 Page - 체크리스트 조회 [JWT O]")
    public ResponseEntity<ResponseData<List<CheckitemDto.Response>>> findCheckList() {
        List<CheckitemDto.Response> checkitemResponseDtoList = checkitemService.findCheckList();
        return ResponseData.toResponseEntity(ResponseCode.READ_CHECKITEM, checkitemResponseDtoList);
    }

    @PostMapping  // 기본 URI path
    @Operation(summary = "메인 Page - 체크리스트 항목 추가 [JWT O]", description = "<strong>!!! 주의사항</strong> : 체크리스트 항목 추가시, 기존 체크리스트에 이미 동일한 항목이름이 있다면 추가 불가능하도록 프론트엔드에서 막을 것 <strong>!!!</strong>")
    public ResponseEntity<ResponseData> saveInCheckList(@RequestBody CheckitemDto.saveRequest saveRequestDto) {
        checkitemService.saveInCheckList(saveRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_CHECKITEM);
    }

    @PutMapping("/{checkitemId}")
    @Operation(summary = "메인 Page - 체크리스트 항목이름/체크여부 변경 [JWT O]",
            description = """
                - <strong>항목이름 변경</strong> : checkitemName = 변경할 항목이름 , isCheck = null
                - <strong>체크여부 변경</strong> : checkitemName = null , isCheck = 변경할 체크여부 (0 or 1)
                - <strong>ERROR 1</strong> : checkitemName = null , isCheck = null
                - <strong>ERROR 2</strong> : checkitemName != null , isCheck != null
                - <strong>ERROR 3</strong> : isCheck = 0 or 1 아닌 다른 숫자의 경우  \n\n<strong>!!! 주의사항</strong> : 체크리스트 항목이름 변경시, 기존 체크리스트에 이미 동일한 항목이름이 있다면 수정 불가능하도록 프론트엔드에서 막을 것 <strong>!!!</strong>
                """)
    public ResponseEntity<ResponseData> updateCheckitem(
            @PathVariable(value = "checkitemId") Long checkitemId,
            @RequestBody CheckitemDto.UpdateRequest updateRequestDto) {
        checkitemService.updateCheckitem(checkitemId, updateRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_CHECKITEM);
    }

    @DeleteMapping("/{checkitemId}")
    @Operation(summary = "메인 Page - 체크리스트 항목 삭제 [JWT O]")
    public ResponseEntity<ResponseData> deleteCheckitem(@PathVariable(value = "checkitemId") Long checkitemId) {
        checkitemService.deleteCheckitem(checkitemId);
        return ResponseData.toResponseEntity(ResponseCode.DELETE_CHECKITEM);
    }
}
