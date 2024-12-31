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

    // 문제 요청 처리
    @PostMapping("/question")
    public String startQuiz(@RequestParam("questionCount") int questionCount,
                            HttpSession session, Model model) {

        int dId = 2;    // 난이도 2(medium) 로 고정, 추후 수정 예정
        session.setAttribute("questionCount", questionCount);   // 세션에 문제 개수 저장
        session.setAttribute("currentQuestionIndex", 0);  // 현제 문제 인덱스 초기화

        // 문제 가져오기
        List<Question> questions = questionService.getQuestionByDifficultyId(dId, questionCount);

        // 문제가 없을 때
        if (questions.isEmpty()) {
            model.addAttribute("error", "문제가 없습니다. 다른 개수를 선택하십시오.");
            return "selectQuestions";
        }

        session.setAttribute("questions", questions);   // 세션에 문제 리스트 저장
        model.addAttribute("question", questions.get(0));   // 첫 번째 문제 모델에 추가
        model.addAttribute("totalQuestions", questionCount);    // 총 문제 수 모델에 추가
        model.addAttribute("currentQuestionIndex", 0);  // 현재 문제 인덱스 모델에 추가
        return "question";
    }

    // 특정 문제 요청 처리
    @GetMapping("/question")
    public ResponseEntity<Question> getQuestionByIndex(HttpSession session) {
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        Integer currentIndex = (Integer) session.getAttribute("currentQuestionIndex");

        if (questions != null && currentIndex != null && currentIndex < questions.size()) {
            Question question = questions.get(currentIndex);
            return ResponseEntity.ok(question); // 문제 반환
        }

        return ResponseEntity.notFound().build();   // 문제가 없음
    }

    @PostMapping("/submitAnswer")
    public String submitAnswer(@RequestParam("selectedAnswer") String selectedAnswer,
                               HttpSession session, Model model) {

        List<Question> questions = (List<Question>) session.getAttribute("questions");
        Integer currentIndex = (Integer) session.getAttribute("currentQuestionIndex");

        if (questions != null && currentIndex != null && currentIndex < questions.size()) {
            Question question = questions.get(currentIndex);
            boolean isCorrect = question.getAnswer() == Integer.parseInt(selectedAnswer);    // 정답 여부 확인

            model.addAttribute("feedback", isCorrect ? "정답" : "오답");

            // 다음 문제로 이동
            currentIndex++;
            session.setAttribute("currentQuestionIndex", currentIndex);

            if (currentIndex < questions.size()) {  // 현재 문제가 마지막 문제가 아닐 경우
                return "redirect:/question";
            } else {                                // 현재 문제가 마지막 문제일 경우
                return "completion";
            }
        }

        return "redirect:/selectQuestions";     // 선택 페이지로 리다이렉트
    }
}
