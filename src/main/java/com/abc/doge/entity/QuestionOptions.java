package com.abc.doge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "question_options")
public class QuestionOptions {
    // 문제 선택지 설정 id PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 질문 id FK
//    @ManyToOne
//    하나의 질문에 여러개의 질문 선택지가 있을 수 있음
//    @JoinColumn(name = "question_id", nullable = false)
//    private Questions question;

    // 하나의 질문에 하나의 질문 선택지가 있음
    @OneToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Questions question; // 연결된 질문

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
}
