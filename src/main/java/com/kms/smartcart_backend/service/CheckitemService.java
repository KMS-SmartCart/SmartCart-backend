package com.kms.smartcart_backend.service;

import com.kms.smartcart_backend.domain.Checkitem;
import com.kms.smartcart_backend.dto.CheckitemDto;

public interface CheckitemService {
    Checkitem findCheckitemByName(String checkitemName);
    void updateCheckitem(CheckitemDto.UpdateRequest updateRequestDto);
    void deleteCheckitem(CheckitemDto.DeleteRequest deleteRequestDto);
}
