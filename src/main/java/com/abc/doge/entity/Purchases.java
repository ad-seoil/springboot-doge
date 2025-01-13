package com.abc.doge.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "purchases")
public class Purchases {
    // 구매 내역 id PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchasesId;

    // 유저는 여러 거래 내역을 가질 수 있음
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberInfo memberInfo;

    // 여러 거래 내역이 하나즤 아이템을 참조하고 있다
    // = 하나의 아이템이 여러 거래 내역에 연결될 수 있음
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Items item;

    // 구매 시각
    private LocalDateTime purchaseDate;
}
