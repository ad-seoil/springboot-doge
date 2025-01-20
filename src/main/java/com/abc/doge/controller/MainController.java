package com.abc.doge.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession; // HttpSession 클래스 import
import org.springframework.ui.Model;     // Model 클래스 import

@Controller
public class MainController {

        // 메인화면
        @GetMapping("/doge")
        public String showMain(HttpSession session, Model model) {
        // 세션에서 로그인 여부 확인
        Object loggedInUser = session.getAttribute("loggedInUser");

        // 로그인 상태를 Model에 추가
        if (loggedInUser != null) {
            model.addAttribute("isLoggedIn", true); // 로그인된 상태
        } else {
            model.addAttribute("isLoggedIn", false); // 비로그인 상태
        }

        return "main"; // "src/main/resources/templates/main.html" 반환
    }

        //학습페이지로 이동
        @GetMapping("/classpage")
        public String classpage(HttpSession session) {
        // 로그인 여부 확인
        Object loggedInUser = session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // 로그인이 되어 있지 않으면 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }
        return "classpage"; // 학습 페이지 반환
    }

        // 메인 페이지 요청 처리
        @GetMapping("/main")
        public String showMainPage() {
            return "main"; // "src/main/resources/templates/main.html" 반환
        }


    }
