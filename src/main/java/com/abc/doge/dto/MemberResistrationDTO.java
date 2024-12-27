package com.abc.doge.dto;

import lombok.Data;

@Data
public class MemberResistrationDTO {
  private String id;                // 사용자 ID
  private String pw;                // 비밀번호
  private String userName;          // 사용자 이름
  private java.sql.Date birthDate;  // 생년월일
  private String nickname;          // 닉네임
  private String email;             // 이메일
}