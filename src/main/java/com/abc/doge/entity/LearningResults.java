package com.abc.doge.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "learning_results")
public class LearningResults {
    // 공부 결과 id PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    // 사용자 id
    // 한 사용자가 여러 번의 학습 결과를 기록할 수 있음
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberInfo memberInfo;

    // 질문 id
    // 한 질문은 여러 학습 결과를 가짐
    @ManyToOne
    @JoinColumn(name = "questions_id", nullable = false)
    private Questions questions;

    // 유저가 고른 답
    @Column(nullable = false)
    private int userAnswer;

    // 정답 유무
    @Column(nullable = false)
    private boolean isCorrect;

    // 문제를 푼 시점의 시간
    private LocalDateTime attemptDate;

}
