package com.kms.smartcart_backend.repository;

import com.kms.smartcart_backend.domain.Checkitem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckitemRepository extends JpaRepository<Checkitem, Long> {
}
