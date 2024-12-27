package com.abc.doge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "member_info")
@Data
public class MemberInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;    // UID

    @Column(name = "PW", nullable = false)
    private String pw;  // password

    @Column(name = "user_name", nullable = false)
    private String userName;  // 사용자 이름

    @Column(name = "birth_date", nullable = false)
    private java.sql.Date birthDate;    // 생년월일

    @Column(name = "email")
    private String email;  // 이메일

    @Column(name = "join_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private java.sql.Timestamp joinDate; // 가입 날짜

    @Column(name = "currency", nullable = false, columnDefinition = "int default 0")
    private Integer currency; // 보유 재화

    @Column(name = "user_level", nullable = false, columnDefinition = "int default 1")
    private Integer userLevel; // 사용자 레벨

    @Column(name = "user_exp", nullable = false, columnDefinition = "int default 0")
    private Integer userExp; // 사용자 경험치
}
