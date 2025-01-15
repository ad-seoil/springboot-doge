package com.abc.doge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "quests")
public class Quests {
    // 퀘스트 id PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 퀘스트 내용
    @Column(length = 255, unique = true)
    private String quest;

    // 보상 경험치
    @Column(name = "reward_exp")
    private int rewardExp;

    // 보상 재화
    @Column(name = "reward_money")
    private int rewardMoney;

    // 퀘스트 기한
    @Column(name = "time_limit")
    private int timeLimit;
}
