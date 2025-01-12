package com.abc.doge.entity;

import com.abc.doge.enums.DailyLearningGoal;
import com.abc.doge.enums.Level;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "study_setting_status")
public class StudySettingStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MemberInfo memberInfo;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Languages language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @Enumerated(EnumType.STRING)
    @Column(name = "daily_learning_goal", nullable = false)
    private DailyLearningGoal dailyLearningGoal;

}
