package com.abc.doge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // 메인화면
    @GetMapping("/doge")
    public String showMain() {
        return "main";  // "src/main/resources/templates/main.html" 반환
    }

    //학습페이지로 이동
    @GetMapping("/classpage")
    public String classpage() {
        return "classpage"; // templates/classpage.html 파일 반환
    }
    // 메인 페이지 요청 처리
    @GetMapping("/main")
    public String showMainPage() {
        return "main"; // "src/main/resources/templates/main.html" 반환
    }
    }
