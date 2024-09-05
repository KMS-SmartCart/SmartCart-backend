package com.kms.smartcart_backend.repository;

import com.kms.smartcart_backend.domain.User;
import com.kms.smartcart_backend.domain.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // '소셜 타입, 식별자'로 해당 회원을 찾기 위한 메소드
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
