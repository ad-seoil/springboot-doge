package com.abc.doge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestController {

    @GetMapping("/Quest-Progress")
    public String showQuestProgressPage() {
        return "Quest_Progress"; // "src/main/resources/templates/Quest_Progress.html" 반환
    }
}
