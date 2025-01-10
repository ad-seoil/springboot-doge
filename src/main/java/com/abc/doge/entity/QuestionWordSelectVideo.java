package com.abc.doge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class QuestionWordSelectVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int qId;
    private int dId;
    private String question;
    private String ex1;
    private String ex2;
    private String ex3;
    private int answer;
    private String videoFile; // 비디오 파일 경로
}
