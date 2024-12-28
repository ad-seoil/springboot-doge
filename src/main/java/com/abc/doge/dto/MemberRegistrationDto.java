package com.abc.doge.dto;

import lombok.Data;

@Data
public class MemberRegistrationDto {
  private String email;             // 이메일
  private String pw;                // 비밀번호
  private String userName;          // 사용자 이름
  private String nickname;          // 닉네임
}