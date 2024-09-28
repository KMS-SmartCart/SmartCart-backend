package com.kms.smartcart_backend.service;

import com.kms.smartcart_backend.domain.Checkitem;
import com.kms.smartcart_backend.dto.CheckitemDto;

import java.util.List;

public interface CheckitemService {
    Checkitem findCheckitem(Long checkitemId);
    List<CheckitemDto.Response> findCheckList();
    void saveInCheckList(CheckitemDto.saveRequest saveRequestDto);
    void updateCheckitem(Long checkitemId, CheckitemDto.UpdateRequest updateRequestDto);
    void deleteCheckitem(Long checkitemId);
    void deleteAllInCheckList();
}
