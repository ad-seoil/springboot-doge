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

//    @OneToOne(mappedBy = "question_id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "question_id")
    private QuestionOptions questionOptions; // QuestionOptions 리스트 추가
    // 리스트가 아니어도 되는 논리적인 이유를 파악할 것
    // 두 테이블간의 관계를 확립할 것

}
