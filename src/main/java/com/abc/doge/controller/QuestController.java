package com.abc.doge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class QuestController {

    private boolean questCompleted = false; // 퀘스트 상태를 저장하는 변수

    @GetMapping("/Quest-Progress")
    public String showQuestProgressPage() {
        return "Quest_Progress"; // "src/main/resources/templates/Quest_Progress.html" 반환
    }

    @GetMapping("/quest/classpage")
    public String showClassPage() {
        return "classpage"; // "src/main/resources/templates/classpage.html" 반환
    }

    @PostMapping("/api/complete-quest")
    @ResponseBody
    public Map<String, String> completeQuest() {
        questCompleted = true; // 퀘스트 완료 상태 설정
        Map<String, String> response = new HashMap<>();
        response.put("message", "퀘스트가 완료되었습니다.");
        return response;
    }

    @GetMapping("/api/quest-status")
    @ResponseBody
    public Map<String, Boolean> getQuestStatus() {
        Map<String, Boolean> response = new HashMap<>();
        response.put("questCompleted", questCompleted); // 퀘스트 완료 상태 반환
        return response;
    }


}