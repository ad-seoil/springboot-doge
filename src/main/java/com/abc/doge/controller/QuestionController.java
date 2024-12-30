package com.abc.doge.controller;

import com.abc.doge.entity.Question;
import com.abc.doge.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    // 문제 개수 선택 페이지 리다이렉트 메서드
    @GetMapping("/selectQuestions")
    public String selectQuestions() {
        return "selectQuestions";
    }


    @PostMapping("/question") // 문제 요청 처리
    public String startQuiz(@RequestParam("questionCount") int questionCount, HttpSession session, Model model) {
        session.setAttribute("questionCount", questionCount); // 세션에 문제 개수 저장
        session.setAttribute("currentQuestionIndex", 0); // 현재 문제 인덱스 초기화
        List<Question> questions = questionService.getQuestions(1, questionCount); // 난이도 1의 문제 가져오기
        session.setAttribute("questions", questions); // 세션에 문제 리스트 저장
        model.addAttribute("question", questions.get(0)); // 첫 번째 문제 모델에 추가
        model.addAttribute("totalQuestions", questionCount); // 총 문제 수 모델에 추가
        return "question"; // 문제 풀이 페이지로 이동 (question.html)
    }

    @GetMapping("/question") // 특정 문제 요청 처리
    public ResponseEntity<Question> getQuestionByIndex(@RequestParam("index") int index, HttpSession session) {
        // 세션에서 문제 리스트를 안전하게 가져오기
        List<Question> questions = (List<Question>) session.getAttribute("questions");

        // null 체크 및 인덱스 범위 확인
        if (questions != null && index >= 0 && index < questions.size()) {
            Question question = questions.get(index);
            return ResponseEntity.ok(question); // 문제 반환
        }

        return ResponseEntity.notFound().build(); // 문제 없음
    }
}
