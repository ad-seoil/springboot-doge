package com.abc.doge.controller;

import com.abc.doge.dto.InquiryDto;
import com.abc.doge.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class InquiryController {

    private final InquiryService inquiryService;

    // 문의페이지 연결
    @GetMapping("/inquiry")
    public String showInquiryForm() {
        return "inquiry";
    }

    @PostMapping("/inquiry")
    public String handleInquiry(InquiryDto inquiryDto, Model model) {
        // 입력 검증
        if (inquiryDto.getInqContent() == null || inquiryDto.getInqContent().isEmpty()) {
            model.addAttribute("error", "문의 내용 필수");
            return "inquiry"; // 오류 발생 시 다시 폼으로 돌아감
        }

        // 문의 메일을 입력받아 DB에 저장
        inquiryService.send(inquiryDto);

        // 결과 페이지로 리다이렉트
        return "redirect:/inquiry";
    }

}
