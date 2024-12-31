package com.abc.doge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InquiryController {

    // 문의페이지 연결
    @GetMapping("/inquiry")
    public String showInquiryForm() {
        return "html/inquiry";
    }

    @PostMapping("/inquiry")
    public String handleInquiry(
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            @RequestParam("email") String email,
            Model model
    ) {
        // 이쪽에서 이메일 전송 또는 데이터 저장 등의 로직

        // 처리 결과를 모델에 추가
        model.addAttribute("successMessage","문의가 성공적으로 제출되었습니다!");

        // 결과 페이지로 리다이렉트 or 포워드
        return "redirect:/inquiry";

    }



    // 문의 내용 DB에 저장

    // 작성한 내용 메일로 발송

    // 성공화면, 실패화면

}
