package com.abc.doge.controller;

import com.abc.doge.entity.MemberInfo;
import com.abc.doge.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.Map;

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
                           @RequestParam(required = false) String birth, // 생년월일 추가 01.11 HSj
                           Model model) {

        // 비밀번호 확인 로직
        if (!pw.equals(password_confirm)) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "register"; // 다시 등록 페이지로 리턴
        }

        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setPassword(pw);
        memberInfo.setNickname(nickname);
        memberInfo.setEmail(email);

        // 생년월일이 입력된 경우 LocalDate로 변환하여 설정 01.11 HSJ
        if (birth != null && !birth.isEmpty()) {
            memberInfo.setBirth(LocalDate.parse(birth)); // yyyy-MM-dd 형식으로 변환
        }

        memberService.registerMember(memberInfo); // 회원 정보 저장
        model.addAttribute("email", email);
        return "success"; // 성공 페이지(success.html)로 리다이렉트
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login";    // login.html
    }

    // 로그인 로직 수정 2025.01.15 HSJ
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String pw,
                        HttpSession session, // 세션추가 2025.01.09 HSJ
                        @RequestParam(required = false) String redirect,
                        Model model) {

        // 이메일을 기준으로 사용자를 조회
        // 해당 이메일에 대한 사용자 정보를 가져오는 서비스 메서드
       MemberInfo memberInfo = memberService.findByEmail(email);

       if (memberInfo != null && memberInfo.getPassword().equals(pw)) {
           // 로그인 성공
           // 세션에 사용자 정보 저장 2025.01.09 HSJ
           session.setAttribute("loggedInUser", memberInfo);

           // redirect 파라미터가 있는 경우 해당 페이지로 리다이렉트
           if (redirect != null && !redirect.isEmpty()) {
               return "redirect:" + redirect;
           }
           // 기본 페이지로 리다이렉트
           return "redirect:/doge";
       } else {
           // 로그인 실패
           model.addAttribute("error", "이메일 또는 비밀번호가 잘못되었습니다.");
           return "login"; // 로그인 페이지로 돌아감
       }
    }

    // 로그아웃 기능
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "main";
    }



}