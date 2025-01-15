package com.abc.doge.entity;

import com.abc.doge.enums.QuestStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "user_quests")
public class userQuests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 퀘스트 진척도의 고유 식별자

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberInfo memberInfo; // 유저 정보 (유저 테이블과의 관계)

    @ManyToOne
    @JoinColumn(name = "quest_id", nullable = false)
    private Quests quests; // 퀘스트 정보 (퀘스트 테이블과의 관계)

    @Enumerated(EnumType.STRING)
    private QuestStatus status; // 퀘스트 상태

    private int progress = 0; // 퀘스트 진행 상황 (0~100) 기본값 0

    // 퀘스르를 받은 날짜
    @Column(name = "created_at", nullable = false, updatable = false )
    private LocalDateTime createdAt;

    // 퀘스트 마지막 업데이트
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // 첫 생성시 현재 시간으로
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 업데이트 전 현재 시간으로 설정
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
