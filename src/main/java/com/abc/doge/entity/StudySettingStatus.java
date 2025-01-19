package com.abc.doge.entity;

import com.abc.doge.enums.DailyLearningGoal;
import com.abc.doge.enums.Level;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "study_setting_status",
        // 한 유저는 여러개의 언어를 배울 수 있지만 한 유저가 똑같은 언어의 테이블을 가질 순 없음
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "language_id"}))

public class StudySettingStatus {
    // 유저별 학습 설문조사 세팅 id PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 한 유저는 여러 개의 언어 학습 설문조사를 가진다
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MemberInfo memberInfo;

    // 여러 학습 설문 조사는 하나의 언어에 대한 설정을 가진다
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Languages language;

    // 유저의 난이도   초급, 중급, 고급, 비즈니스
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    // 일일 학습 목표량   MIN_3, MIN_5, MIN_15, MIN_30, HOUR_1
    @Enumerated(EnumType.STRING)
    @Column(name = "daily_learning_goal", nullable = false)
    private DailyLearningGoal dailyLearningGoal;

}
