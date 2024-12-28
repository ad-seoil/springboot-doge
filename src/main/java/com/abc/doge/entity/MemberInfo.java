package com.abc.doge.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "member_info")
@Data
public class MemberInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;    // UID

    @Column(name = "PW", nullable = false)
    private String pw;  // password

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;  // 이메일

    @Column(name = "join_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private java.sql.Timestamp joinDate; // 가입 날짜

    @Column(name = "last_updated_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private java.sql.Timestamp lastUpdatedDate; // 마지막 업데이트 날짜

    @Column(name = "currency", nullable = false, columnDefinition = "int default 0")
    private Integer currency; // 보유 재화

    @Column(name = "user_level", nullable = false, columnDefinition = "int default 1")
    private Integer userLevel; // 사용자 레벨

    @Column(name = "user_exp", nullable = false, columnDefinition = "int default 0")
    private Integer userExp; // 사용자 경험치

    public MemberInfo() {
        // 기본값 생성
        this.currency = 0;
        this.userLevel = 1;
        this.userExp = 0;
        this.joinDate = Timestamp.valueOf(LocalDateTime.now()); // 현재시간 설정
        this.lastUpdatedDate = Timestamp.valueOf(LocalDateTime.now()); // 현재시간 설정
    }
}
