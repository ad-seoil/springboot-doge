package com.abc.doge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "questions_word_select")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String ex1;     // 보기 1
    private String ex2;     // 보기 2
    private String ex3;     // 보기 3
    private String answer;
    private int d_id;   // 난이도 ID
}
