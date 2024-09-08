package com.kms.smartcart_backend.repository;

import com.kms.smartcart_backend.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByUser_Id(Long userId);
}
