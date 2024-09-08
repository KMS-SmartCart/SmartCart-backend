package com.kms.smartcart_backend.service;

import com.kms.smartcart_backend.domain.Checkitem;
import com.kms.smartcart_backend.dto.CheckitemDto;

import java.util.List;

public interface CheckitemService {
    Checkitem findCheckitemByName(String checkitemName);
    List<CheckitemDto.Response> findCheckList();
    void saveInCheckList(CheckitemDto.saveRequest saveRequestDto);
    void updateCheckitem(CheckitemDto.UpdateRequest updateRequestDto);
    void deleteCheckitem(CheckitemDto.DeleteRequest deleteRequestDto);
}
