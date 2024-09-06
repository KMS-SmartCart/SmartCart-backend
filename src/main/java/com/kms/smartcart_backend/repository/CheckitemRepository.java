package com.kms.smartcart_backend.repository;

import com.kms.smartcart_backend.domain.Checkitem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckitemRepository extends JpaRepository<Checkitem, Long> {

    // '로그인사용자id, 항목이름'으로 해당 체크리스트 항목을 찾기 위한 메소드
    Optional<Checkitem> findByUser_IdAndCheckitemName(Long userId, String checkitemName);
}
