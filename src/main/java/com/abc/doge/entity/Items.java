package com.abc.doge.entity;

import com.abc.doge.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "items")
public class Items {
    // 아이템 id PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 아이템 이름명
    @Column(nullable = false, length = 100)
    private String name;

    // 아이템 설명
    @Column(columnDefinition = "TEXT")
    private String description;

    // 아이템 가격
    @Column(nullable = false)
    private int price;

    // 결제 타입 CASH, IN_GAME_CASH
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;
}
