package com.abc.doge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "question_options")
public class QuestionOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 각 테이블이 어떤 데이터를 저장하고 있는지 확실히 할 것

    @OneToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Questions questions;

    @Column(nullable = false, length = 255)
    private String ex1;

    @Column(nullable = false, length = 255)
    private String ex2;

    @Column(nullable = false, length = 255)
    private String ex3;

    @Column(nullable = false)
    private int answer;
}
