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

    private LocalDateTime createdDate;

    private LocalDateTime lastUpdatedDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionOptions> options; // QuestionOptions 리스트 추가
}
