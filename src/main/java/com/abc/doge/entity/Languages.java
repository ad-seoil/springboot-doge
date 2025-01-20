package com.abc.doge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "languages")
public class Languages {

    // 언어 id PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 언어 명
    @Column(nullable = false, length = 100, unique = true)
    private String languageName;
}
