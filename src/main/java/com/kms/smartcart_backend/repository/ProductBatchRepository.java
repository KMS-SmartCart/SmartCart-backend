package com.kms.smartcart_backend.repository;

import com.kms.smartcart_backend.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductBatchRepository {  // 대용량 데이터의 batch 처리를 위한 JDBC Repository

    private final JdbcTemplate jdbcTemplate;
    private static final int BATCH_SIZE = 1000;  // 배치 크기 설정 (메모리 오버헤드 방지)


    public void batchDelete(List<Product> productList) {

        for (int i=0; i<productList.size(); i+=BATCH_SIZE) {
            List<Long> batchList = productList.subList(i, Math.min(i+BATCH_SIZE, productList.size()))
                    .stream()
                    .map(Product::getId)
                    .collect(Collectors.toList());

            String sql = String.format("DELETE FROM product WHERE product_id IN (%s)",
                    batchList.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(",")));

            jdbcTemplate.update(sql);
        }
    }
}
