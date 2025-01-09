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
}
