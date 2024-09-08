package com.kms.smartcart_backend.service.impl;

import com.kms.smartcart_backend.domain.Checkitem;
import com.kms.smartcart_backend.domain.User;
import com.kms.smartcart_backend.dto.CheckitemDto;
import com.kms.smartcart_backend.repository.CheckitemRepository;
import com.kms.smartcart_backend.response.exception.Exception400;
import com.kms.smartcart_backend.response.exception.Exception404;
import com.kms.smartcart_backend.service.CheckitemService;
import com.kms.smartcart_backend.service.UserService;
import com.kms.smartcart_backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckitemServiceImpl implements CheckitemService {

    private final UserService userService;
    private final CheckitemRepository checkitemRepository;


    @Transactional(readOnly = true)
    @Override
    public Checkitem findCheckitemByName(String checkitemName) {
        Long loginUserId = SecurityUtil.getCurrentMemberId();  // 로그인 사용자id 활용. (+ 로그인 체킹 용도)
        Checkitem checkitem = checkitemRepository.findByUser_IdAndCheckitemName(loginUserId, checkitemName).orElseThrow(
                () -> new Exception404.NoSuchCheckitem(String.format("userId = %d & checkitemName = %s", loginUserId, checkitemName)));
        return checkitem;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CheckitemDto.Response> findCheckList() {
        Long loginUserId = SecurityUtil.getCurrentMemberId();
        List<Checkitem> checkitemList = checkitemRepository.findAllByUser_Id(loginUserId);

        return checkitemList.stream()
                .sorted(Comparator.comparing(Checkitem::getId))  // id 기준 오름차순 정렬
                .map(CheckitemDto.Response::new)  // DTO 변환
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void saveInCheckList(CheckitemDto.saveRequest saveRequestDto) {
        User user = userService.findLoginUser();
        Checkitem checkitem = Checkitem.CheckitemSaveBuilder()
                .checkitemName(saveRequestDto.getCheckitemName())
                .user(user)
                .build();
        checkitemRepository.save(checkitem);
    }

    @Transactional
    @Override
    public void updateCheckitem(CheckitemDto.UpdateRequest updateRequestDto) {
        String beforeName = updateRequestDto.getBeforeName();
        String afterName = updateRequestDto.getAfterName();
        Integer isCheck = updateRequestDto.getIsCheck();
        Checkitem checkitem = findCheckitemByName(beforeName);  // (+ 로그인 체킹)

        if(beforeName != null && afterName != null && isCheck == null) checkitem.updateCheckitemName(afterName);
        else if(beforeName != null && afterName == null && isCheck != null) {
            if(isCheck == 0 || isCheck == 1) checkitem.updateIsCheck(isCheck);
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
