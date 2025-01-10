package com.abc.doge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class QuestionConversationTts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int qId;
    private int dId;
    private String question; // TTS로 변환될 질문
    private String ex1;
    private String ex2;
    private String ex3;
    private int answer;
}
