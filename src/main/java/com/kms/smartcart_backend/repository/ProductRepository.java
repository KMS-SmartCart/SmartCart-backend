package com.kms.smartcart_backend.repository;

import com.kms.smartcart_backend.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
