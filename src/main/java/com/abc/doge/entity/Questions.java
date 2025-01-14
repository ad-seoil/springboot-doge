package com.abc.doge.entity;

import com.abc.doge.enums.QuestionLevel;
import com.abc.doge.enums.QuestionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "questions")
public class Questions {
    // 질문 id PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 질문의 유형 TTS_SELECT, IMAGE_SELECT, VIDEO_SELECT, TEXT_SELECT
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    // 질문의 난이도 EASY, MEDIUM, HARD
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionLevel questionLevel;

    // 질문 내용
    @Column(nullable = false, length = 255)
    private String questionText;

    // 질문에 쓰이는 파일의 이름
    @Column(length = 255)
    private String questionFile;

    // 예시 1
    @Column(nullable = false, length = 255)
    private String ex1;

    // 예시2
    @Column(nullable = false, length = 255)
    private String ex2;

    // 예시3
    @Column(nullable = false, length = 255)
    private String ex3;

    // 정답
    @Column(nullable = false)
    private int answer;

    // 문제 생성 날짜
    @Column(name = "created_date", nullable = false, updatable = false )
    private LocalDateTime createdDate;

    // 문제 마지막 업데이트 날짜
    @Column(name = "last_update_date", nullable = false)
    private LocalDateTime lastUpdatedDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now(); // 생성 시 현재 시간으로 설정
        // 생성시 현재 시간으로 설정
        // 이 부분이 누락이라 에러가 났었음 2025.01.14 HSJ
        this.lastUpdatedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdatedDate = LocalDateTime.now(); // 업데이트 전 현재 시간으로 설정
    }

}
