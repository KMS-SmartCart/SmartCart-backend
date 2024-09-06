package com.kms.smartcart_backend.service.impl;

import com.kms.smartcart_backend.domain.Checkitem;
import com.kms.smartcart_backend.dto.CheckitemDto;
import com.kms.smartcart_backend.repository.CheckitemRepository;
import com.kms.smartcart_backend.response.exception.Exception400;
import com.kms.smartcart_backend.response.exception.Exception404;
import com.kms.smartcart_backend.service.CheckitemService;
import com.kms.smartcart_backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckitemServiceImpl implements CheckitemService {

    private final CheckitemRepository checkitemRepository;


    @Transactional(readOnly = true)
    @Override
    public Checkitem findCheckitemByName(String checkitemName) {
        Long loginUserId = SecurityUtil.getCurrentMemberId();  // 로그인 사용자id 활용. (+ 로그인 체킹 용도)
        Checkitem checkitem = checkitemRepository.findByUser_IdAndCheckitemName(loginUserId, checkitemName).orElseThrow(
                () -> new Exception404.NoSuchCheckitem(String.format("userId = %d & checkitemName = %s", loginUserId, checkitemName)));
        return checkitem;
    }

    @Transactional
    @Override
    public void updateCheckitem(CheckitemDto.UpdateRequest updateRequestDto) {
        String beforeName = updateRequestDto.getBeforeName();
        String afterName = updateRequestDto.getAfterName();
        Integer checked = updateRequestDto.getChecked();
        Checkitem checkitem = findCheckitemByName(beforeName);  // (+ 로그인 체킹)

        if(beforeName != null && afterName != null && checked == null) checkitem.updateCheckitemName(afterName);
        else if(beforeName != null && afterName == null && checked != null) {
            if(checked == 0 || checked == 1) checkitem.updateChecked(checked);
            else throw new Exception400.CheckitemBadRequest("잘못된 요청값으로 API를 요청하였습니다.");
        }
        else throw new Exception400.CheckitemBadRequest("잘못된 요청값으로 API를 요청하였습니다.");
    }

    @Transactional
    @Override
    public void deleteCheckitem(CheckitemDto.DeleteRequest deleteRequestDto) {
        Checkitem checkitem = findCheckitemByName(deleteRequestDto.getCheckitemName());  // (+ 로그인 체킹)
        checkitemRepository.delete(checkitem);
    }
}
