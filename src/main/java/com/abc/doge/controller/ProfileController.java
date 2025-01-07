package com.abc.doge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    // 프로필 페이지
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

}
