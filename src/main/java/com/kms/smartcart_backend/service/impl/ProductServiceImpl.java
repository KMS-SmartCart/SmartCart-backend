package com.kms.smartcart_backend.service.impl;

import com.kms.smartcart_backend.domain.Product;
import com.kms.smartcart_backend.domain.User;
import com.kms.smartcart_backend.dto.ProductDto;
import com.kms.smartcart_backend.repository.ProductBatchRepository;
import com.kms.smartcart_backend.repository.ProductRepository;
import com.kms.smartcart_backend.response.exception.Exception400;
import com.kms.smartcart_backend.service.ProductService;
import com.kms.smartcart_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final ProductBatchRepository productBatchRepository;


    @Transactional
    @Override
    public void saveInBasket(ProductDto.SaveRequest saveRequestDto) {
        User loginUser = userService.findLoginUser();

        // 아낀 금액
        Integer savedMoney = saveRequestDto.getSavedMoney();
        if(savedMoney != null && savedMoney > 0) loginUser.addSavedMoney(savedMoney);
        else throw new Exception400.ProductBadRequest("잘못된 요청값으로 API를 요청하였습니다.");

        // 선택 상품 (오프라인 or 온라인)
        Integer selectType = saveRequestDto.getSelectType();
        Integer isSelectOffline = 0, isSelectOnline = 0;
        if(selectType == 0) isSelectOffline = 1;  // 선택한 항목이 오프라인 상품인 경우
        else if(selectType == 1) isSelectOnline = 1;  // 선택한 항목이 온라인 상품인 경우
        else throw new Exception400.ProductBadRequest("잘못된 요청값으로 API를 요청하였습니다.");

        Product offlineProduct = Product.ProductSaveBuilder()
                .isOnline(0)
                .isSelect(isSelectOffline)
                .productName(saveRequestDto.getOfflineProductName())
                .price(saveRequestDto.getOfflinePrice())
                .amount(saveRequestDto.getOfflineAmount())
                .user(loginUser)
                .build();
        Product onlineProduct = Product.ProductSaveBuilder()
                .isOnline(1)
                .isSelect(isSelectOnline)
                .productName(saveRequestDto.getOnlineProductName())
                .price(saveRequestDto.getOnlinePrice())
                .amount(saveRequestDto.getOnlineAmount())
                .user(loginUser)
                .build();
        List<Product> productList = Arrays.asList(offlineProduct, onlineProduct);

        productBatchRepository.batchInsert(productList);  // JDBC의 batch insert를 활용하여, 대용량 Batch 저장 처리. (DB 여러번 접근 방지 & 성능 향상)
    }
}
