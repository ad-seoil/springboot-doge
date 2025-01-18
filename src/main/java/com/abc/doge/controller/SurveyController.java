package com.abc.doge.controller;

import com.abc.doge.entity.Languages;
import com.abc.doge.entity.MemberInfo;
import com.abc.doge.entity.StudySettingStatus;
import com.abc.doge.enums.DailyLearningGoal;
import com.abc.doge.enums.Level;
import com.abc.doge.service.SurveyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@SessionAttributes("loggedInUser")  // 로그인한 사용자 정보를 세션에 저장
public class SurveyController {

    @Autowired
    private SurveyService surveyService; // 언어 서비스

    // 설문조사 페이지로 이동
    @GetMapping("/survey")
    public String getLanguageSurvey(HttpServletRequest request, Model model,
                                    @SessionAttribute(name = "loggedInUser", required = false) String user) {
//  그러고 보면 설문조사는 해당 언어에 대해서는 하나면 되는데 만약 설문조사가 똑같은 언어로 클릭하지 못하게 예외처리 해야하는 거 아닌가
        if (user == null) {
            // 로그인하지 않은 경우, 로그인 페이지로 리다이렉트
            return "redirect:/login?redirect=" + request.getRequestURI();
        }
        // 모든 언어 가져오기
        List<Languages> languages = surveyService.findAll();

        // 언어명 id를 기준으로 정렬
        languages.sort(Comparator.comparing(Languages::getId));

        model.addAttribute("languages", languages); // 모델에 추가
        return "survey/survey_lang"; // survey.html로 이동
    }

    // 선택한 학습 언어 저장
    @PostMapping("/saveLanguage")
    public ResponseEntity<String> saveLanguage(@RequestParam Long languageId,
                                               @SessionAttribute(name = "loggedInUser")MemberInfo memberInfo) {
        // 선택한 언어를 DB에 저장
        // 언어 ID로 언어 조회
        Languages language = surveyService.findLanguageById(languageId);

        if (language != null) {
            StudySettingStatus studySettingStatus = new StudySettingStatus();
            // 현재 로그인된 사용자 정보
            studySettingStatus.setMemberInfo(memberInfo);
            // 선택한 언어
            studySettingStatus.setLanguage(language);

            // 기본 값 설정 ( 예시로 난이도와 목표량 설정) <- 나중에 또 고쳐야함
            studySettingStatus.setLevel(Level.초급);
            studySettingStatus.setDailyLearningGoal(DailyLearningGoal.MIN_5);

            // db에 저장
            surveyService.saveStudySetingStatus(studySettingStatus);

            return ResponseEntity.ok("언어 저장 성공");
        } else {
            return ResponseEntity.badRequest().body("언어를 찾을 수 없습니다");
        }

    }

    // 학습 목적 페이지 이동
    @GetMapping("surveyLevel")
    public String getLevelSurvey(HttpServletRequest request, Model model,
                                    @SessionAttribute(name = "loggedInUser", required = false) String user) {
        if (user == null) {
            // 로그인하지 않은 경우, 로그인 페이지로 리다이렉트
            return "redirect:/login?redirect=" + request.getRequestURI();
        }

        // 레벨 목록을 모델에 추가
        model.addAttribute("levels", Level.values());

        return "survey/survey_level";
    }




}