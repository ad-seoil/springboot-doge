package com.abc.doge.controller;

import com.abc.doge.entity.QuestionOptions;
import com.abc.doge.entity.Questions;
import com.abc.doge.enums.QuestionLevel;
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
        return "selectQuestions"; // 질문 선택 페이지로 이동
    }

    // entity수정과 함께 난이도를 가져오는 코드 부분 수정 2025.01.12 HSJ
    @PostMapping("/startQuestion")
    public String startQuestion(@RequestParam("questionLevel") QuestionLevel questionLevel, // ENUM 타입으로 변경
                                @RequestParam("questionCount") int questionCount,
                                HttpSession session) {

        session.setAttribute("questionLevel", questionLevel); // ENUM을 세션에 저장
        session.setAttribute("questionCount", questionCount);

        // 무작위 질문 가져오기
        List<Questions> questions = questionService.getRandomQuestionsByLevel(questionLevel, questionCount);

        if (questions == null || questions.isEmpty()) {
            return "redirect:/selectQuestions"; // 질문이 없으면 선택 화면으로 이동
        }

        session.setAttribute(SESSION_QUESTIONS, questions);
        session.setAttribute(SESSION_CURRENT_INDEX, 0);
        session.setAttribute(SESSION_CORRECT_ANSWERS, 0);
        session.setAttribute("totalQuestions", questions.size());
        return "redirect:/question"; // 질문 화면으로 이동
    }

    @GetMapping("/question")
    public String getQuestion(HttpSession session,
                              @RequestParam(value = "index", required = false) Integer index,
                              Model model) {
        List<Questions> questions = (List<Questions>) session.getAttribute(SESSION_QUESTIONS);
        Integer currentIndex = (index != null) ? index : (Integer) session.getAttribute(SESSION_CURRENT_INDEX);

        if (currentIndex == null) {
            currentIndex = 0; // 기본값 설정
        }

        if (questions == null || currentIndex == null || currentIndex >= questions.size()) {
            return "redirect:/completion"; // 모든 문제 푼 경우 완료 페이지로 이동
        }

        Questions question = questions.get(currentIndex);
        model.addAttribute("question", question);
        model.addAttribute("currentQuestionIndex", currentIndex);
        model.addAttribute("totalQuestions", questions.size());

        session.setAttribute(SESSION_CURRENT_INDEX, currentIndex);

        return "question"; // 질문 화면 반환
    }

    @PostMapping("/submitAnswer")
    public String submitAnswer(@RequestParam("selectedAnswer") int selectedAnswer, HttpSession session) {
        List<Questions> questions = (List<Questions>) session.getAttribute(SESSION_QUESTIONS);
        Integer currentIndex = (Integer) session.getAttribute(SESSION_CURRENT_INDEX);
        Integer correctAnswers = (Integer) session.getAttribute(SESSION_CORRECT_ANSWERS);

        if (correctAnswers == null) correctAnswers = 0;
        if (currentIndex == null) currentIndex = 0;

        if (questions != null && currentIndex < questions.size()) {
            Questions question = questions.get(currentIndex);
            // 정답 확인
            boolean isCorrect = false;

            // 선택한 답안이 정답인지 확인
            if (selectedAnswer >= 1 && selectedAnswer <= 3) { // 예를 들어, 1, 2, 3 중 하나
                QuestionOptions option = question.getOption(); // 0-indexed -> 단일 선택지 가져오기
                // 이전에는 QuestionOptions가 리스트 형태였기 때문에 특정 인덱스에 접근하기 위해서는 selectedAnswer-1을 사용
                // 그러나 QuestionOptions가 단일 객채로 변경되면서 선택한 답안(selectAnswer)을 그대로 가져오면 됨
                if (option.getAnswer() == selectedAnswer) {
                    isCorrect = true; // 정답일 경우
                }
            }

            if (isCorrect) {
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

        return "redirect:/completion"; // 모든 문제 푼 경우 완료 페이지로 이동
    }

    @GetMapping("/completion")
    public String completion(Model model, HttpSession session) {
        Integer totalQuestions = (Integer) session.getAttribute("totalQuestions");
        Integer correctAnswers = (Integer) session.getAttribute(SESSION_CORRECT_ANSWERS);

        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute(SESSION_CORRECT_ANSWERS, correctAnswers);

        return "completion"; // completion.html을 반환
    }

    // 결과 페이지 테스트용
    @GetMapping("/completion2")
    public String completion2(Model model, HttpSession session) {
        Integer totalQuestions = (Integer) session.getAttribute("totalQuestions");
        Integer correctAnswers = (Integer) session.getAttribute(SESSION_CORRECT_ANSWERS);

        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("totalXP", totalQuestions + (correctAnswers * 10));

        // 정확도 계산 (0으로 나누는 경우 방지)
        if (totalQuestions != null && totalQuestions > 0) {
            int accuracy = (int) ((correctAnswers / (double) totalQuestions) * 100);
            model.addAttribute("accuracy", accuracy);
        } else {
            model.addAttribute("accuracy", 0); // 총 문제 수가 0이면 정확도 0
        }

        return "questionResult"; // 결과 페이지 반환
    }
}
