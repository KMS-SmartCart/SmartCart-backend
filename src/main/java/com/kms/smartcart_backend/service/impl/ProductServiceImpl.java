package com.kms.smartcart_backend.service.impl;

import com.kms.smartcart_backend.domain.Product;
import com.kms.smartcart_backend.domain.User;
import com.kms.smartcart_backend.dto.ProductDto;
import com.kms.smartcart_backend.repository.ProductBatchRepository;
import com.kms.smartcart_backend.repository.ProductRepository;
import com.kms.smartcart_backend.response.exception.Exception400;
import com.kms.smartcart_backend.service.ProductService;
import com.kms.smartcart_backend.service.UserService;
import com.kms.smartcart_backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final ProductBatchRepository productBatchRepository;


    @Transactional(readOnly = true)
    @Override
    public ProductDto.BasketResponse findBasket() {
        Long loginUserId = SecurityUtil.getCurrentMemberId();

        // id 기준 오름차순 정렬
        List<Product> productList = productRepository.findAllByUser_Id(loginUserId).stream()
                .sorted(Comparator.comparing(Product::getId))
                .collect(Collectors.toList());

        // 온라인 or 오프라인 분류
        List<ProductDto.Response> offlineList = new ArrayList<>(), onlineList = new ArrayList<>();
        Integer offlinePriceSum = 0, onlinePriceSum = 0, allPriceSum = 0;
        for(Product product : productList) {
            ProductDto.Response productResponseDto = new ProductDto.Response(product);
            if(product.getIsOnline() == 0) {  // 오프라인
                offlineList.add(productResponseDto);
                if(product.getIsSelect() == 1) offlinePriceSum += product.getPrice();
            }
            else {  // 온라인
                onlineList.add(productResponseDto);
                if(product.getIsSelect() == 1) onlinePriceSum += product.getPrice();
            }
        }
        allPriceSum = offlinePriceSum + onlinePriceSum;

        // 절약한 총 금액 계산
        Integer savedMoneySum = 0;
        for(int i=0; i<offlineList.size(); i++) {
            ProductDto.Response offlineDto = offlineList.get(i);
            ProductDto.Response onlineDto = onlineList.get(i);

            Integer isSelectOffline = offlineDto.getIsSelect();
            Integer offlinePrice = offlineDto.getPrice();
            Integer onlinePrice = onlineDto.getPrice();

            Integer savedMoney = calculateSavedMoney(isSelectOffline, offlinePrice, onlinePrice);
            savedMoneySum += savedMoney;
        }

        return ProductDto.BasketResponse.builder()
                .offlineList(offlineList)
                .onlineList(onlineList)
                .offlinePriceSum(offlinePriceSum)
                .onlinePriceSum(onlinePriceSum)
                .allPriceSum(allPriceSum)
                .savedMoneySum(savedMoneySum)
                .build();
    }

    @Transactional
    @Override
    public void saveInBasket(ProductDto.SaveRequest saveRequestDto) {
        User user = userService.findLoginUser();

        // 선택 상품 (오프라인 or 온라인)
        Integer selectType = saveRequestDto.getSelectType();
        Integer isSelectOffline = 0, isSelectOnline = 0;
        if(selectType != null && selectType == 0) isSelectOffline = 1;  // 선택한 항목이 오프라인 상품인 경우
        else if(selectType != null && selectType == 1) isSelectOnline = 1;  // 선택한 항목이 온라인 상품인 경우
        else throw new Exception400.ProductBadRequest("잘못된 요청값으로 API를 요청하였습니다.");

        // 아낀 금액
        Integer savedMoney = calculateSavedMoney(isSelectOffline, saveRequestDto.getOfflinePrice(), saveRequestDto.getOnlinePrice());
        if(savedMoney > 0) user.addSavedMoney(savedMoney);

        Product offlineProduct = Product.ProductSaveBuilder()
                .isOnline(0)
                .isSelect(isSelectOffline)
                .productName(saveRequestDto.getOfflineProductName())
                .price(saveRequestDto.getOfflinePrice())
                .amount(saveRequestDto.getOfflineAmount())
                .user(user)
                .build();
        Product onlineProduct = Product.ProductSaveBuilder()
                .isOnline(1)
                .isSelect(isSelectOnline)
                .productName(saveRequestDto.getOnlineProductName())
                .price(saveRequestDto.getOnlinePrice())
                .amount(saveRequestDto.getOnlineAmount())
                .user(user)
                .build();
        List<Product> productList = Arrays.asList(offlineProduct, onlineProduct);

        productBatchRepository.batchInsert(productList);  // JDBC의 batch insert를 활용하여, 대용량 Batch 저장 처리. (DB 여러번 접근 방지 & 성능 향상)
    }

    @Transactional
    @Override
    public void deleteAllInBasket() {
        User user = userService.findLoginUser();
        List<Product> productList = user.getProductList();
        productBatchRepository.batchDelete(productList);
    }


    // ========== 유틸성 메소드 ========== //

    private static Integer calculateSavedMoney(Integer isSelectOffline, Integer offlinePrice, Integer onlinePrice) {
        if(isSelectOffline == 1) {
            // 오프라인 선택 && 오프라인 저렴 일때
            if(offlinePrice < onlinePrice) return onlinePrice - offlinePrice;
            // 오프라인 선택 && 오프라인 비쌈 일때
            else return 0;
        }
        else {
            // 온라인 선택 && 온라인 저렴 일때
            if(offlinePrice > onlinePrice) return offlinePrice - onlinePrice;
            // 온라인 선택 && 온라인 비쌈 일때
            else return 0;
        }
    }
}
