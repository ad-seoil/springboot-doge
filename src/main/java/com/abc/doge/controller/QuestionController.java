package com.abc.doge.controller;

import com.abc.doge.entity.LearningResults;
import com.abc.doge.entity.Questions;
import com.abc.doge.enums.QuestionLevel;
import com.abc.doge.enums.QuestionType;
import com.abc.doge.service.LearningResultService;
import com.abc.doge.service.MemberInfoService;
import com.abc.doge.service.QuestionsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class QuestionController {
    private static final String SESSION_QUESTIONS = "questions";
    private static final String SESSION_CURRENT_INDEX = "currentQuestionIndex";
    private static final String SESSION_CORRECT_ANSWERS = "correctAnswers";

    @Autowired
    private QuestionsService questionService;

    @Autowired
    private LearningResultService learningResultService;

    @Autowired
    private MemberInfoService memberInfoService;

    @GetMapping("/questionTest1")
    public String selectStage() {
        return "select_stage";
    }

    // 테스트페이지 1에서 단어 선택 버튼 클릭시
    @GetMapping("/select/TEXT_SELECT")
    public String selectWordSelect(HttpSession session) {
        session.setAttribute("questionType", "TEXT_SELECT");
        return "redirect:/selectQuestions";
    }
    // 테스트페이지 1에서 이미지_듣기 버튼 클릭시
    @GetMapping("/select/IMAGE_SELECT")
    public String selectTtsSelectImage(HttpSession session) {
        session.setAttribute("questionType", "IMAGE_SELECT");
        return "redirect:/selectQuestions";
    }
    // 테스트페이지 1에서 단어 선택_영상 버튼 클릭시
    @GetMapping("/select/VIDEO_SELECT")
    public String selectWordSelectVideo(HttpSession session) {
        session.setAttribute("questionType", "VIDEO_SELECT");
        return "redirect:/selectQuestions";
    }
    // 테스트페이지 1에서 듣기_대화 버튼 클릭시
    @GetMapping("/select/TTS_SELECT")
    public String selectConversationTts(HttpSession session) {
        session.setAttribute("questionType", "TTS_SELECT");
        return "redirect:/selectQuestions";
    }

    @GetMapping("/selectQuestions")
    public String selectQuestions(
            @RequestParam("questionType") QuestionType questionType,
            Model model
    ) {
        model.addAttribute("questionType", questionType);
        System.out.println("Received questionType: " + questionType);
        return "selectQuestions"; // 질문 선택 페이지로 이동
    }

    // entity수정과 함께 난이도를 가져오는 코드 부분 수정 2025.01.12 HSJ
    @PostMapping("/startQuestion")
    public String startQuestion(
            @RequestParam("questionType") QuestionType questionType,
            @RequestParam("questionLevel") QuestionLevel questionLevel, // ENUM 타입으로 변경
            @RequestParam("questionCount") int questionCount,
            HttpSession session
    ) {
        System.out.println("Received questionLevel: " + questionLevel);
        System.out.println("Received questionCount: " + questionCount);
        System.out.println("Received questionType: " + questionType);

        session.setAttribute("questionLevel", questionLevel); // ENUM을 세션에 저장
        session.setAttribute("questionCount", questionCount);
        session.setAttribute("questionType", questionType);

        // 무작위 질문 가져오기
        List<Questions> questions = getRandomQuestions(questionType, questionLevel, questionCount);

        if (questions == null || questions.isEmpty()) {
            // 문제 리스트를 못 가지고 왔을때의 처리
            // 디버그 메세지를 넣어둬야한다.
            System.out.println("문제리스트를 가져오지 못했습니다.: ");
            return "redirect:/selectQuestions?questionType="+questionType; // 질문이 없으면 선택 화면으로 이동
        }

        session.setAttribute(SESSION_QUESTIONS, questions);
        session.setAttribute(SESSION_CURRENT_INDEX, 0);
        session.setAttribute(SESSION_CORRECT_ANSWERS, 0);
        session.setAttribute("totalQuestions", questions.size());

        System.out.println("questionType: " + questionType);
        System.out.println("questionLevel: " + questionLevel);

        return "redirect:/question"; // 질문 화면으로 이동
    }

    // 문제 목록 가져오기
    private List<Questions> getRandomQuestions(QuestionType questionType, QuestionLevel questionLevel, int questionCount) {
        List<Questions> questions
                = questionService.getQuestionsByQuestionTypeAndQuestionLevel(questionType, questionLevel);
        Collections.shuffle(questions);
        return questions.subList(0, Math.min(questionCount, questions.size()));
    }

    @GetMapping("/question")
    public String getQuestion(
            HttpSession session,
            @RequestParam(value = "index", required = false) Integer index,
            Model model
    ) {
        List<Questions> questions = (List<Questions>) session.getAttribute(SESSION_QUESTIONS);
        Integer currentIndex = (index != null) ? index : (Integer) session.getAttribute(SESSION_CURRENT_INDEX);
        String questionType = session.getAttribute("questionType").toString();


        System.out.println("questions: " + questions);
        System.out.println("currentIndex: " + currentIndex);

        if (currentIndex == null) {
            currentIndex = 0; // 기본값 설정
        }

        if (questions == null) {
            System.out.println("questions is null");
            return "redirect:/completion";
        }

        Questions question = questions.get(currentIndex);

        Long id = question.getId();

        // questionType에 따라 필요한 정보를 모델에 추가
        switch (questionType) {
            case "IMAGE_SELECT":
                model.addAttribute("imageUrl", question.getQuestionFile());
                model.addAttribute("ex1Audio", "/audio/ttsSelectImage/ID_" + id + "_ex1.wav");
                model.addAttribute("ex2Audio", "/audio/ttsSelectImage/ID_" + id + "_ex2.wav");
                model.addAttribute("ex3Audio", "/audio/ttsSelectImage/ID_" + id + "_ex3.wav");
                break;
            case "VIDEO_SELECT":
                model.addAttribute("videoUrl", question.getQuestionFile());
                break;
            case "TTS_SELECT":
                model.addAttribute("questionAudio", "/audio/conversationTts/ID_" + id + ".wav");
                break;
            default: // TEXT_SELECT
                break;
        }

        // questionText를 question 객체에서 가져와서 모델에 추가
        String questionText = question.getQuestionText();

        // questionText가 null이면 빈 문자열("")로 설정(IMAGE_SELECT, TTS_SELECT)
        if (questionText == null) {
            questionText = "";
        }

        model.addAttribute("questionText", questionText);
        // 보기를 리스트에 담아서 모델에 추가
        List<String> options = List.of(question.getEx1(), question.getEx2(), question.getEx3());
        model.addAttribute("options", options);

        model.addAttribute("question", question);
        model.addAttribute("currentQuestionIndex", currentIndex);
        model.addAttribute("totalQuestions", questions.size());
        model.addAttribute("questionType", questionType);

        session.setAttribute(SESSION_CURRENT_INDEX, currentIndex);

        return "question"; // 질문 화면 반환
    }

    // 답안 제출 메서드
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
            boolean isCorrect = question.getAnswer() == selectedAnswer;

//             선택한 답안이 정답인지 확인
//            if (selectedAnswer >= 1 && selectedAnswer <= 3) { // 예를 들어, 1, 2, 3 중 하나
//                QuestionOptions option = question.getOption(); // 0-indexed -> 단일 선택지 가져오기
//                 이전에는 QuestionOptions가 리스트 형태였기 때문에 특정 인덱스에 접근하기 위해서는 selectedAnswer-1을 사용
//                 그러나 QuestionOptions가 단일 객채로 변경되면서 선택한 답안(selectAnswer)을 그대로 가져오면 됨
//                if (option.getAnswer() == selectedAnswer) {
//                    isCorrect = true; // 정답일 경우
//                }
//            }

            // 학습 결과 저장
            LearningResults result = new LearningResults();
            result.setMemberInfo(memberInfoService.findById(1L)); // memberInfo 설정
            result.setQuestions(question); // questions 설정
            result.setUserAnswer(selectedAnswer);
            result.setCorrect(isCorrect); // 정답 여부 저장
            learningResultService.saveResult(result);


            if (isCorrect) {
                correctAnswers++; // 정답일 경우 맞춘 문제 수 증가
            }

            session.setAttribute(SESSION_CORRECT_ANSWERS, correctAnswers); // 세션에 맞춘 문제 수 저장
            session.setAttribute(SESSION_CURRENT_INDEX, ++currentIndex);   // 현재 문제 인덱스값 증가

            return "redirect:/nextQuestion";    // nextQuestion 메서드로 리다이렉트
        }

        return "redirect:/completion"; // 예외상황(추후 다른 페이지로 바꿔야함)
    }

    @GetMapping("/nextQuestion")
    public String nextQuestion(HttpSession session) {
        List<Questions> questions = (List<Questions>) session.getAttribute(SESSION_QUESTIONS);
        Integer currentIndex = (Integer) session.getAttribute(SESSION_CURRENT_INDEX);
        Integer correctAnswers = (Integer) session.getAttribute(SESSION_CORRECT_ANSWERS);

        if (questions != null && currentIndex < questions.size()) {
            return "redirect:/question"; // 다음 문제로 이동
        } else {
            // 모든 문제를 풀었을 때 정답률 계산 및 completion 메서드로 리다이렉트
            double accuracy = (double) correctAnswers / questions.size() * 100;
            session.setAttribute("accuracy", accuracy); // 정답률 세션에 저장
            return "redirect:/completion"; // 모든 문제를 푼 경우 완료 페이지로 이동
        }
    }

    @GetMapping("/completion")
    public String completion(Model model, HttpSession session) {
        Integer totalQuestions = (Integer) session.getAttribute("totalQuestions");
        Integer correctAnswers = (Integer) session.getAttribute(SESSION_CORRECT_ANSWERS);
        double accuracy = (double) session.getAttribute("accuracy"); // 정답률 가져오기

        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute(SESSION_CORRECT_ANSWERS, correctAnswers);
        model.addAttribute("accuracy", accuracy); // 정답률 모델에 추가

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
