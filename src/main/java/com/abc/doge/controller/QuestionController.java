package com.abc.doge.controller;

import com.abc.doge.entity.Question;
import com.abc.doge.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/selectQuestions")
    public String selectQuestions() {
        return "selectQuestions";
    }

    @PostMapping("/startQuestion")
    public String startQuestion(@RequestParam("difficultyId") int difficultyId,
                                @RequestParam("questionCount") int questionCount,
                                HttpSession session) {
        session.setAttribute("difficultyId", difficultyId);
        session.setAttribute("questionCount", questionCount);

        List<Question> questions = questionService.getRandomQuestionsByDId(difficultyId, questionCount);

        if (questions == null || questions.isEmpty()) {
            return "redirect:/selectQuestions";
        }

        session.setAttribute("questions", questions);
        session.setAttribute("currentQuestionIndex", 0);
        session.setAttribute("correctAnswers", 0); // 맞춘 문제 수 초기화
        return "redirect:/question";
    }

    @GetMapping("/question")
    @ResponseBody
    public Question getQuestion(HttpSession session,
                                @RequestParam(value = "index", required = false) Integer index) {
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        Integer currentIndex = (index != null) ? index : (Integer) session.getAttribute("currentQuestionIndex");

        if (questions == null || currentIndex == null || currentIndex >= questions.size()) {
            return null; // 문제가 없거나 인덱스 초과 시 null 반환
        }

        return questions.get(currentIndex); // 현재 문제 반환
    }

    @PostMapping("/submitAnswer")
    public String submitAnswer(@RequestParam("selectedAnswer") int selectedAnswer, HttpSession session) {
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        Integer currentIndex = (Integer) session.getAttribute("currentQuestionIndex");
        Integer correctAnswers = (Integer) session.getAttribute("correctAnswers");

        if (questions != null && currentIndex != null) {
            Question question = questions.get(currentIndex);
            // 정답 여부 확인
            if (selectedAnswer == question.getAnswer()) {
                correctAnswers++; // 정답일 경우 맞춘 문제 수 증가
            }
            session.setAttribute("correctAnswers", correctAnswers); // 세션에 맞춘 문제 수 저장

            currentIndex++;
            session.setAttribute("currentQuestionIndex", currentIndex);

            if (currentIndex < questions.size()) {
                return "redirect:/question"; // 다음 문제로 이동
            }
        }
        return "redirect:/completion"; // 모든 문제를 푼 경우 완료 페이지로 이동
    }

}
