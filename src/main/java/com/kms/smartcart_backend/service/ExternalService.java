package com.kms.smartcart_backend.service;

import com.kms.smartcart_backend.dto.ExternalDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ExternalService {
    ExternalDto.ChatgptImageProcessingResponse getImageInfo(MultipartFile imageFile) throws IOException;
    List<ExternalDto.NaverShoppingResponse> getLowPriceProducts(String query);
}
