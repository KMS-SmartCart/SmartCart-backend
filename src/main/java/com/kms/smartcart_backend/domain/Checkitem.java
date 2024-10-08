package com.kms.smartcart_backend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

@Table(name ="checkitem")  // check는 MySQL 테이블명으로 사용 불가능.
@Entity
public class Checkitem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checkitem_id")
    private Long id;

    @Column(name = "is_check", columnDefinition = "TINYINT(1) default 0", length = 1)
    private Integer isCheck;  // 체킹(1) or 미체킹(0)

    @Column(name = "checkitem_name")
    private String checkitemName;

    @ManyToOne(fetch = FetchType.LAZY)  // User-Checkitem 양방향매핑
    @JoinColumn(name = "user_id")
    private User user;


    @Builder(builderClassName = "CheckitemSaveBuilder", builderMethodName = "CheckitemSaveBuilder")
    public Checkitem(String checkitemName, User user) {
        // 이 빌더는 체크리스트 아이템등록때만 사용할 용도
        this.isCheck = 0;
        this.checkitemName = checkitemName;
        this.user = user;
    }


    public void updateIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public void updateCheckitemName(String checkitemName) {
        this.checkitemName = checkitemName;
    }
}
