package com.abc.doge.entity;

import com.abc.doge.enums.QuestionLevel;
import com.abc.doge.enums.QuestionType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "questions")
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionLevel questionLevel;

    @Column(nullable = false, length = 255)
    private String questionText;

    @Column(length = 255)
    private String questionFile;

    @Column(nullable = false, length = 255)
    private String ex1;

    @Column(nullable = false, length = 255)
    private String ex2;

    @Column(nullable = false, length = 255)
    private String ex3;

    @Column(nullable = false)
    private int answer;

    @CreationTimestamp
    private Timestamp createdDate;

    @CreationTimestamp
    private Timestamp lastUpdatedDate;

}
