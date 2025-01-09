package com.abc.doge.controller;

import com.abc.doge.entity.MemberInfo;
import com.abc.doge.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/profile")
    public String getProfile(HttpSession session, Model model) {
        String email = (String) session.getAttribute("loggedInUserEmail"); // 세션에서 이메일 가져오기

        if (email != null) {
            MemberInfo memberInfo = memberService.findByEmail(email); // 이메일로 회원 정보 조회
//            StudyStatus studyStatus = userService.getStudyStatus(memberInfo.getUID());

            model.addAttribute("memberInfo", memberInfo);
//            model.addAttribute("studyStatus", studyStatus);
        } else {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        return "profile"; // profile.html로 이동
    }

}
