package com.abc.doge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "member_info")
@Data
public class MemberInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
}
