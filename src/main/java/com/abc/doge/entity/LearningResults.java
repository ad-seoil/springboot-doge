package com.abc.doge.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "learning_results")
public class LearningResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberInfo memberInfo;

    @ManyToOne
    @JoinColumn(name = "questions_id", nullable = false)
    private Questions questions;

    @Column(nullable = false)
    private int userAnswer;

    @Column(nullable = false)
    private boolean isCorrect;

    private LocalDateTime attemptDate;

}
