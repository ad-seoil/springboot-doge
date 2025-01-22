package com.abc.doge.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/leaderboard")
public class leaderboardController {

    // 데이터 저장소 (임시 더미 데이터)
    private final List<Map<String, Object>> data = new ArrayList<>(List.of(
            createUserData(1, "12345", "홍길동", "hong@gmail.com", "영국", 1500, 0),
            createUserData(2, "67890", "김철수", "chulsoo@gmail.com", "미국", 1400, 0),
            createUserData(3, "11223", "이영희", "yeonghee@gmail.com", "미국", 1300, 0)
    ));

    // HTML 템플릿 반환 (리더보드 페이지)
    @GetMapping
    public String getLeaderboard(Model model) {
        // 데이터를 Model에 추가
        model.addAttribute("users", data);
        return "leaderboard"; // templates/leaderboard.html 반환
    }

    // 좋아요 업데이트 API
    @PostMapping("/like/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateLikes(@PathVariable String id) {
        for (Map<String, Object> row : data) {
            if (row.get("id").equals(id)) {
                int updatedLikes = (int) row.get("likes") + 1;
                row.put("likes", updatedLikes);
                return ResponseEntity.ok(row); // 업데이트된 데이터를 반환
            }
        }
        return ResponseEntity.notFound().build(); // ID가 없을 경우 404 반환
    }

    // 더미 데이터 생성 메서드
    private static Map<String, Object> createUserData(int rank, String id, String name, String email, String language, int points, int likes) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("rank", rank);
        userData.put("id", id);
        userData.put("name", name);
        userData.put("email", email);
        userData.put("language", language);
        userData.put("points", points);
        userData.put("likes", likes);
        return userData;
    }
}
