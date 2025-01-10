package com.abc.doge.controller;

import com.abc.doge.entity.QuestionConversationTts;
import com.abc.doge.entity.QuestionTtsSelectImage;
import com.abc.doge.entity.QuestionWordSelect;
import com.abc.doge.entity.QuestionWordSelectVideo;
import com.abc.doge.service.QuestionConversationTtsService;
import com.abc.doge.service.QuestionTtsSelectImageService;
import com.abc.doge.service.QuestionWordSelectService;
import com.abc.doge.service.QuestionWordSelectVideoService;
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
    private QuestionWordSelectService questionWordSelectService;
    @Autowired
    private QuestionTtsSelectImageService questionTtsSelectImageService;
    @Autowired
    private QuestionWordSelectVideoService questionWordSelectVideoService;
    @Autowired
    private QuestionConversationTtsService questionConversationTtsService;

    // 문제별 id 확인
    private Long getIdFromQuestionObj(Object questionObj) {
        if (questionObj instanceof QuestionWordSelect) {
            return  ((QuestionWordSelect) questionObj).getId();
        } else if (questionObj instanceof QuestionTtsSelectImage) {
            return  ((QuestionTtsSelectImage) questionObj).getId();
        } else if (questionObj instanceof QuestionWordSelectVideo) {
            return  ((QuestionWordSelectVideo) questionObj).getId();
        } else if ((questionObj instanceof QuestionConversationTts)) {
            return  ((QuestionConversationTts) questionObj).getId();
        } else {
            throw new IllegalArgumentException("Invalid question type");
        }
    }

    // 정답 확인 (questionType에 따라 다른 메서드 호출)
    private int getCorrectAnswer(Object questionObj) {
        if (questionObj instanceof QuestionWordSelect) {
            return ((QuestionWordSelect) questionObj).getAnswer();
        } else if (questionObj instanceof QuestionTtsSelectImage) {
            return ((QuestionTtsSelectImage) questionObj).getAnswer();
        } else if (questionObj instanceof QuestionWordSelectVideo) {
            return ((QuestionWordSelectVideo) questionObj).getAnswer();
        } else if (questionObj instanceof QuestionConversationTts) {
            return ((QuestionConversationTts) questionObj).getAnswer();
        } else {
            throw new IllegalArgumentException("Invalid question type");
        }
    }

    // 문제 가져오기
    private List<?> getRandomQuestions(int questionType, int difficultyId, int questionCount) {
        return switch (questionType) {
            case 1 -> questionWordSelectService.getRandomQuestionsByDId(difficultyId, questionCount);
            case 2 -> questionTtsSelectImageService.getRandomQuestionsByDId(difficultyId, questionCount);
            case 3 -> questionWordSelectVideoService.getRandomQuestionsByDId(difficultyId, questionCount);
            case 4 -> questionConversationTtsService.getRandomQuestionsByDId(difficultyId, questionCount);
            default -> null;
        };
    }

    // 문제 테스트 페이지 1
    @GetMapping("/questionTest1")
    public String selectStage() {
        return "selectStage";
    }

    // 테스트페이지 1에서 단어 선택 버튼 클릭시
    @GetMapping("/select/1")
    public String selectWordSelect(HttpSession session) {
        session.setAttribute("questionType", 1);
        return "redirect:/selectQuestions";
    }
    // 테스트페이지 1에서 이미지_듣기 버튼 클릭시
    @GetMapping("/select/2")
    public String selectTtsSelectImage(HttpSession session) {
        session.setAttribute("questionType", 2);
        return "redirect:/selectQuestions";
    }
    // 테스트페이지 1에서 단어 선택_영상 버튼 클릭시
    @GetMapping("/select/3")
    public String selectWordSelectVideo(HttpSession session) {
        session.setAttribute("questionType", 3);
        return "redirect:/selectQuestions";
    }
    // 테스트페이지 1에서 듣기_대화 버튼 클릭시
    @GetMapping("/select/4")
    public String selectConversationTts(HttpSession session) {
        session.setAttribute("questionType", 4);
        return "redirect:/selectQuestions";
    }

    @GetMapping("/selectQuestions")
    public String selectQuestions() {
        return "selectQuestions";
    }

    @PostMapping("/startQuestion")
    public String startQuestion(
            @RequestParam("difficultyId") int difficultyId,
            @RequestParam("questionCount") int questionCount,
            HttpSession session
    ) {
        int questionType = (int) session.getAttribute("questionType");

        session.setAttribute("difficultyId", difficultyId);
        session.setAttribute("questionCount", questionCount);

        List<?> questions = getRandomQuestions(questionType, difficultyId, questionCount);

        if (questions == null || questions.isEmpty()) {
            return "redirect:/selectQuestions";
        }

        session.setAttribute(SESSION_QUESTIONS, questions);
        session.setAttribute(SESSION_CURRENT_INDEX, 0);
        session.setAttribute(SESSION_CORRECT_ANSWERS, 0);
        session.setAttribute("totalQuestions", questions.size());

        return "redirect:/question";
    }

    // 문제 페이지
    @GetMapping("/question")
    public String getQuestion(
            HttpSession session,
            @RequestParam(value = "index", required = false) Integer index,
            Model model
    ) {
        List<?> questions = (List<?>) session.getAttribute(SESSION_QUESTIONS);
        Integer currentIndex = (index != null) ? index : (Integer) session.getAttribute(SESSION_CURRENT_INDEX);
        int questionType = (int) session.getAttribute("questionType");

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

        Object questionObj = questions.get(currentIndex);
        Long id = getIdFromQuestionObj(questionObj);

        if (questionType == 2) {
            model.addAttribute("ex1Audio", "/audio/ttsSelectImage/ID_" + id + "_ex1.wav");
            model.addAttribute("ex2Audio", "/audio/ttsSelectImage/ID_" + id + "_ex2.wav");
            model.addAttribute("ex3Audio", "/audio/ttsSelectImage/ID_" + id + "_ex3.wav");
        } else if (questionType == 4) {
            model.addAttribute("questionAudio", "/audio/conversationTts/ID_" + id + ".wav");
        }

        model.addAttribute("question", questions.get(currentIndex));
        model.addAttribute("currentQuestionIndex", currentIndex);
        model.addAttribute("totalQuestions", questions.size());
        model.addAttribute("questionType", questionType);

        session.setAttribute("currentQuestionIndex", currentIndex);

        return "question";
    }

    @PostMapping("/submitAnswer")
    public String submitAnswer(
            @RequestParam("selectedAnswer") int selectedAnswer,
            HttpSession session
    ) {
        List<?> questions = (List<?>) session.getAttribute(SESSION_QUESTIONS);
        Integer currentIndex = (Integer) session.getAttribute(SESSION_CURRENT_INDEX);
        Integer correctAnswers = (Integer) session.getAttribute(SESSION_CORRECT_ANSWERS);

        if (correctAnswers == null) correctAnswers = 0;
        if (currentIndex == null) currentIndex = 0;

        if (questions != null && currentIndex < questions.size()) {
            Object questionObj = questions.get(currentIndex);
            int correctAnswer = getCorrectAnswer(questionObj);
            // 정답 여부 확인
            if (selectedAnswer == correctAnswer) {
                correctAnswers++; // 정답일 경우 맞춘 문제 수 증가
            }

            session.setAttribute(SESSION_CORRECT_ANSWERS, correctAnswers); // 세션에 맞춘 문제 수 저장
            session.setAttribute(SESSION_CURRENT_INDEX, ++currentIndex);

            if (currentIndex < questions.size()) {
                return "redirect:/question"; // 다음 문제로 이동
            } else {
                return "redirect:/completion2"; // 모든 문제를 푼 경우 완료 페이지로 이동
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

    // 결과 페이지 테스트용 2025.01.07 HSJ
    @GetMapping("/completion2")
    public String completion2(Model model, HttpSession session) {
        Integer totalQuestions = (Integer) session.getAttribute("totalQuestions");
        Integer correctAnswers = (Integer) session.getAttribute(SESSION_CORRECT_ANSWERS);

        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("totalXP",totalQuestions+(correctAnswers*10));
        // 정확도 계산 (0으로 나누는 경우 방지)
        if (totalQuestions != null && totalQuestions > 0) {
            // 정확도를 자연수로 계산
            int accuracy = (int) ((correctAnswers / (double) totalQuestions) * 100);
            model.addAttribute("accuracy", accuracy);
        } else {
            model.addAttribute("accuracy", 0); // 총 문제 수가 0이면 정확도 0
        }

        return "questionResult";
    }


}
