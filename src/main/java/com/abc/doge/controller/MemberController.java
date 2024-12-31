package com.abc.doge.controller;

import com.abc.doge.entity.MemberInfo;
import com.abc.doge.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // register.html
    }

    @PostMapping("/register")
    public String register(@RequestParam String pw,
                           @RequestParam String password_confirm,
                           @RequestParam String nickname,
                           @RequestParam String email,
                           Model model) {

        // 비밀번호 확인 로직
        if (!pw.equals(password_confirm)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "register"; // 다시 등록 페이지로 리턴
        }

        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setPw(pw);
        memberInfo.setNickname(nickname);
        memberInfo.setEmail(email);

        memberService.registerMember(memberInfo); // 회원 정보 저장
        model.addAttribute("email", email);
        return "success"; // 성공 페이지(success.html)로 리다이렉트
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login";    // login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String pw,
                        Model model) {
       MemberInfo memberInfo = memberService.findByEmail(email);

       if (memberInfo != null && memberInfo.getPw().equals(pw)) {
           // 로그인 성공
           model.addAttribute("message", "로그인 성공");
           return "redirect:/";
       } else {
           // 로그인 실패
           model.addAttribute("error", "이메일 또는 비밀번호가 잘못되었습니다.");
           return "login";
       }
    }
}