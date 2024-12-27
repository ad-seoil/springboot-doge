package com.abc.doge.dto;

import lombok.Data;

@Data
public class MemberLoginDto {
    private String identifier;   // 사용자 ID 또는 이메일
    private String pw;
}
