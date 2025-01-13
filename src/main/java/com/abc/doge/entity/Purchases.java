package com.abc.doge.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "purchases")
public class Purchases {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchasesId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberInfo memberInfo;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Items item;

    private LocalDateTime purchaseDate;
}
