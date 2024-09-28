package com.kms.smartcart_backend.service.impl;

import com.kms.smartcart_backend.domain.Checkitem;
import com.kms.smartcart_backend.domain.User;
import com.kms.smartcart_backend.dto.CheckitemDto;
import com.kms.smartcart_backend.repository.CheckitemBatchRepository;
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
    private final CheckitemBatchRepository checkitemBatchRepository;


    @Transactional(readOnly = true)
    @Override
    public Checkitem findCheckitem(Long checkitemId) {
        Long loginUserId = SecurityUtil.getCurrentMemberId();  // 로그인 사용자id 활용. (+ 로그인 체킹 용도)
        Checkitem checkitem = checkitemRepository.findByUser_IdAndId(loginUserId, checkitemId).orElseThrow(
                () -> new Exception404.NoSuchCheckitem(String.format("userId = %d & checkitemId = %d", loginUserId, checkitemId)));
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
    public void updateCheckitem(Long checkitemId, CheckitemDto.UpdateRequest updateRequestDto) {
        Checkitem checkitem = findCheckitem(checkitemId);  // (+ 로그인 체킹)
        String checkitemName = updateRequestDto.getCheckitemName();
        Integer isCheck = updateRequestDto.getIsCheck();

        if(checkitemName != null && isCheck == null) checkitem.updateCheckitemName(checkitemName);
        else if(checkitemName == null && isCheck != null) {
            if(isCheck == 0 || isCheck == 1) checkitem.updateIsCheck(isCheck);
            else throw new Exception400.CheckitemBadRequest("잘못된 요청값으로 API를 요청하였습니다.");
        }
        else throw new Exception400.CheckitemBadRequest("잘못된 요청값으로 API를 요청하였습니다.");
    }

    @Transactional
    @Override
    public void deleteCheckitem(Long checkitemId) {
        Checkitem checkitem = findCheckitem(checkitemId);  // (+ 로그인 체킹)
        checkitemRepository.delete(checkitem);
    }

    @Transactional
    @Override
    public void deleteAllInCheckList() {
        User user = userService.findLoginUser();
        List<Checkitem> checkitemList = user.getCheckitemList();
        checkitemBatchRepository.batchDelete(checkitemList);
    }
}
