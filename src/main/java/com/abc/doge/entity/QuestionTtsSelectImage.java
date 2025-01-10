package com.abc.doge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class QuestionTtsSelectImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 문제 ID

    private int q_id;   // 문제 유형 ID (easy = 2, medium = 6, hard = 10)
    private int d_id;   // 난이도 ID
    private String image_file;  // 이미지 파일 경로 (question 칼럼 대체)
    private String ex1;     // 보기 1, tts로 변환
    private String ex2;     // 보기 2, tts로 변환
    private String ex3;     // 보기 3, tts로 변환
    private int answer;     // 정답 인덱스(1 or 2 or 3)
}
