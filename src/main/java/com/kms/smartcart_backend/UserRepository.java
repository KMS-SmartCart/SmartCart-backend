package com.kms.smartcart_backend;

import com.kms.smartcart_backend.domain.User;
import com.kms.smartcart_backend.domain.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // '소셜 타입, 식별자'로 해당 회원을 찾기 위한 메소드
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    // userId로 검색하여 refreshToken만 가져오는 메소드
    @Query("SELECT u.refreshToken FROM User u WHERE u.id = :userId")
    String findRefreshTokenById(@Param("userId") Long userId);
}
