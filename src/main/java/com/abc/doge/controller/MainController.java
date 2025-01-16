package com.abc.doge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // 메인화면
    @GetMapping("/doge")
    public String showMain() {
        return "main";
    }

    //학습페이지로 이동
    @GetMapping("/classpage")
    public String classpage() {
        return "classpage"; // templates/classpage.html 파일 반환
    }
}
