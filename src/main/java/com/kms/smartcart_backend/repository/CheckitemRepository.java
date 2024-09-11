package com.kms.smartcart_backend.repository;

import com.kms.smartcart_backend.domain.Checkitem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheckitemRepository extends JpaRepository<Checkitem, Long> {

    // '로그인사용자id, 항목id'으로 해당 체크리스트 항목을 찾기 위한 메소드
    Optional<Checkitem> findByUser_IdAndId(Long userId, Long checkitemId);

    List<Checkitem> findAllByUser_Id(Long userId);
}
