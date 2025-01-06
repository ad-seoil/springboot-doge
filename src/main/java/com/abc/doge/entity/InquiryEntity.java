package com.abc.doge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자 추가
@Table(name = "inquiry")
public class InquiryEntity {

    // 문의 메일 Id Pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiry_id;

    // 문의 메일 제목
    @Column(name = "inq_title", nullable = false)
    private String inqTitle;

    // 문의 메일 내용
    @Column(name = "inq_content", nullable = false)
    private String inqContent;

    // 답변받을 메일 주소
    @Column(name = "inq_email", nullable = false)
    private String inqEmail;

    // 문의메일 발송 시간
    @Column(name = "send_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private java.sql.Timestamp sendDate;

    @PrePersist // 엔티티가 영속화되기 전에 실행
    public void prePersist() {
        this.sendDate = Timestamp.valueOf(LocalDateTime.now()); // 현재 시간으로 발송시간 설정
    }
}
