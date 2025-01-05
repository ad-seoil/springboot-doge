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

import java.util.List;

@Controller
public class QuestionController {
    private static final String SESSION_QUESTIONS = "questions";
    private static final String SESSION_CURRENT_INDEX = "currentQuestionIndex";
    private static final String SESSION_CORRECT_ANSWERS = "correctAnswers";
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

        session.setAttribute(SESSION_QUESTIONS, questions);
        session.setAttribute(SESSION_CURRENT_INDEX, 0);
        System.out.println("currentQuestionIndex: " + session.getAttribute("currentQuestionIndex"));
        session.setAttribute(SESSION_CORRECT_ANSWERS, 0);
        session.setAttribute("totalQuestions", questions.size());
        return "redirect:/question";
    }

    @GetMapping("/question")
    public String getQuestion(HttpSession session,
                                @RequestParam(value = "index", required = false) Integer index,
                                Model model) {
        List<Question> questions = (List<Question>) session.getAttribute(SESSION_QUESTIONS);
        Integer currentIndex = (index != null) ? index : (Integer) session.getAttribute(SESSION_CURRENT_INDEX);

        // currentIndex가 null일 경우, 세션에 있는 currentQuestionIndex의 기본값으로 설정
        if (currentIndex == null) {
            currentIndex = (Integer) session.getAttribute("currentQuestionIndex");

            // 여전히 null일 경우, 안전하게 0으로 설정
            if (currentIndex == null) {
                currentIndex = 0; // 기본값 설정
            }
        }

        // 상태 확인을 위한 로그 출력
        System.out.println("Current Index: " + currentIndex);
        System.out.println("Questions list size: " + (questions != null ? questions.size() : 0));

        if (questions == null || currentIndex == null) {
            return "redirect:/selectQuestions";
        } else if (currentIndex >= questions.size()) {
            return "redirect:/completion";
        }

        model.addAttribute("question", questions.get(currentIndex));
        model.addAttribute("currentQuestionIndex", currentIndex);
        model.addAttribute("totalQuestions", questions.size());

        session.setAttribute("currentQuestionIndex", currentIndex);

        return "question";
    }

    @PostMapping("/submitAnswer")
    public String submitAnswer(@RequestParam("selectedAnswer") int selectedAnswer, HttpSession session) {
        List<Question> questions = (List<Question>) session.getAttribute(SESSION_QUESTIONS);
        Integer currentIndex = (Integer) session.getAttribute(SESSION_CURRENT_INDEX);
        Integer correctAnswers = (Integer) session.getAttribute(SESSION_CORRECT_ANSWERS);

        if (correctAnswers == null) correctAnswers = 0;
        if (currentIndex == null) currentIndex = 0;

        if (questions != null && currentIndex < questions.size()) {
            Question question = questions.get(currentIndex);
            // 정답 여부 확인
            if (selectedAnswer == question.getAnswer()) {
                correctAnswers++; // 정답일 경우 맞춘 문제 수 증가
            }
            session.setAttribute(SESSION_CORRECT_ANSWERS, correctAnswers); // 세션에 맞춘 문제 수 저장
            session.setAttribute(SESSION_CURRENT_INDEX, ++currentIndex);

            if (currentIndex < questions.size()) {
                return "redirect:/question"; // 다음 문제로 이동
            } else {
                return "redirect:/completion"; // 모든 문제를 푼 경우 완료 페이지로 이동
            }
        }

        return "redirect:/completion";
    }

    @GetMapping("/completion")
    public String completion(Model model, HttpSession session) {
        Integer totalQuestions = (Integer) session.getAttribute("totalQuestions");
        Integer correctAnswers = (Integer) session.getAttribute(SESSION_CORRECT_ANSWERS);

        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute(SESSION_CORRECT_ANSWERS, correctAnswers);

        return "completion"; // completion.html을 반환
    }

}
