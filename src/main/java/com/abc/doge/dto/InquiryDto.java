package com.abc.doge.dto;

import lombok.Data;

@Data
public class InquiryDto {
    // 문의 메일 제목
    String inqTitle;
    // 문의 메일 내용
    String inqContent;
    // 답장 받을 메일 주소
    String inqEmail;
}
