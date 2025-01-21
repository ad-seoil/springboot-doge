package com.abc.doge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RankingController {

    @GetMapping("/User_Ranking")
    public String getUserRanking(Model model) {
        // 샘플 데이터 (실제로는 DB 또는 서비스에서 데이터를 가져옵니다)
        List<UserRanking> rankings = new ArrayList<>();
        rankings.add(new UserRanking(1, "Alice", 5000));
        rankings.add(new UserRanking(2, "Bob", 4500));
        rankings.add(new UserRanking(3, "Charlie", 4000));

        // 모델에 데이터를 추가
        model.addAttribute("rankings", rankings);

        // ranking.html 템플릿 반환
        return "ranking";
    }

    // 내부 클래스: 실제로는 별도의 파일로 관리합니다
    public static class UserRanking {
        private int rank;
        private String username;
        private int points;

        public UserRanking(int rank, String username, int points) {
            this.rank = rank;
            this.username = username;
            this.points = points;
        }

        public int getRank() {
            return rank;
        }

        public String getUsername() {
            return username;
        }

        public int getPoints() {
            return points;
        }
    }
}