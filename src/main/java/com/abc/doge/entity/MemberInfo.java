package com.abc.doge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_info")
public class MemberInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;  // member_id

    @Column(name = "email", nullable = false, unique = true, length = 80)
    private String email;  // 이메일

    @Column(name = "pw", nullable = false, length = 30)
    private String password;  // 비밀번호

    @Column(name = "nickname", nullable = false, length = 30)
    private String nickname;  // 닉네임

    @Column(name = "birth")
    private LocalDate birth;  // 생년월일

    @Builder.Default
    @Column(name = "user_exp", nullable = false)
    private int userExp = 0;  // 사용자 경험 (기본값 0)

    @Builder.Default
    @Column(name = "user_money", nullable = false)
    private int userMoney = 0;  // 사용자 재화 (기본값 0)

    @Builder.Default
    @Column(name = "join_date", nullable = false, updatable = false)
    private Timestamp joinDate = Timestamp.valueOf(LocalDateTime.now());  // 가입 날짜

    @Builder.Default
    @Column(name = "last_update_date", nullable = false)
    private Timestamp lastUpdateDate = Timestamp.valueOf(LocalDateTime.now());  // 마지막 업데이트 날짜

    @PreUpdate
    public void preUpdate() {
        this.lastUpdateDate = Timestamp.valueOf(LocalDateTime.now());  // 업데이트 전 현재 시간으로 설정
    }
}
