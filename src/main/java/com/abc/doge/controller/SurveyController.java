package com.abc.doge.controller;

import com.abc.doge.dto.SettingInfoDto;
import com.abc.doge.dto.StudySettingStatusDto;
import com.abc.doge.entity.Languages;
import com.abc.doge.entity.MemberInfo;
import com.abc.doge.entity.SettingInfo;
import com.abc.doge.entity.StudySettingStatus;
import com.abc.doge.enums.DailyLearningGoal;
import com.abc.doge.enums.Level;
import com.abc.doge.service.SettingInfoService;
import com.abc.doge.service.StudySettingStatusService;
import com.abc.doge.service.SurveyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private SettingInfoService settingInfoService;

    @Autowired
    private StudySettingStatusService studySettingStatusService;

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
                                               @SessionAttribute(name = "loggedInUser")MemberInfo memberInfo,
                                               HttpSession session) {
        // 선택한 언어를 DB에 저장
        // 언어 ID로 언어 조회
        Languages language = surveyService.findLanguageById(languageId);

        if (language != null) {
            // 유저가 이미 이 언어의 정보를 가지고 있는지 확인
            boolean exists = surveyService.existsByUserAndLanguage(memberInfo.getMemberId(), languageId);
            if (exists) {
                return ResponseEntity.badRequest().body("이미 학습 중인 언어입니다.");
            }

            // 선택한 언어를 세션에 저장
            session.setAttribute("selectedLanguage", language);

//            이건 선택 한번 할 때마다 바로바로 db에 저장하는 코드
//            StudySettingStatus studySettingStatus = new StudySettingStatus();
//            // 현재 로그인된 사용자 정보
//            studySettingStatus.setMemberInfo(memberInfo);
//            // 선택한 언어
//            studySettingStatus.setLanguage(language);
//
//            // 기본 값 설정 ( 예시로 난이도와 목표량 설정) <- 나중에 또 고쳐야함
//            studySettingStatus.setLevel(Level.초급);
//            studySettingStatus.setDailyLearningGoal(DailyLearningGoal.MIN_5);
//
//            // db에 저장
//            surveyService.saveStudySetingStatus(studySettingStatus);

            return ResponseEntity.ok("언어 저장 성공");
        } else {
            return ResponseEntity.badRequest().body("언어를 찾을 수 없습니다");
        }

    }

    // 언어별 개인 실력 페이지 이동
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

    // 선택한 실력 저장
    @PostMapping("/saveLevel")
    public ResponseEntity<String> saveLevel(@RequestBody StudySettingStatusDto studySettingStatusDto,
                                            @SessionAttribute(name = "loggedInUser") MemberInfo memberInfo,
                                            HttpSession session) {
        try {
            // 문자열로 레벨을 가져옵니다.
            Level level = Level.valueOf(studySettingStatusDto.getLevel());

            // 선택한 레벨을 세션에 저장
            session.setAttribute("selectedLevel", level);

            return ResponseEntity.ok("레벨 저장 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("유효하지 않은 레벨입니다.");
        }
    }

    // 일일 학습 목표 설문 페이지
    @GetMapping("/surveyDaily")
    public String getSurveyDaily(HttpServletRequest request, Model model,
                                 @SessionAttribute(name = "loggedInUser", required = false) String user) {
        if (user == null) {
            // 로그인하지 않은 경우, 로그인 페이지로 리다이렉트
            return "redirect:/login?redirect=" + request.getRequestURI();
        }

        return "survey/survey_Daily";
    }

    // 일일 학습 목표 저장
    @PostMapping("/saveDaily")
    public ResponseEntity<String> saveDaily(@RequestBody StudySettingStatusDto studySettingStatusDto,
                                            @SessionAttribute(name = "loggedInUser") MemberInfo memberInfo,
                                            HttpSession session) {
        // 목표 값을 가져옵니다.
        String goal = studySettingStatusDto.getGoal();

        // 세션에 목표 값을 저장합니다.
        session.setAttribute("dailyLearningGoal", goal);

        return ResponseEntity.ok("목표가 세션에 저장되었습니다.");
    }

    // 알람 설정 페이지
    @GetMapping("/surveyAlarm")
    public String getSurveyAlarm(HttpServletRequest request, Model model,
                                 @SessionAttribute(name = "loggedInUser", required = false) String user) {
        if (user == null) {
            // 로그인하지 않은 경우, 로그인 페이지로 리다이렉트
            return "redirect:/login?redirect=" + request.getRequestURI();
        }

        return "survey/survey_Alarm";
    }

    // 설문 데이터 저장
    @PostMapping("/saveSurveyData")
    public ResponseEntity<String> saveSurveyData(@RequestBody SettingInfoDto settingInfoDto,
                                                 @SessionAttribute(name = "loggedInUser") MemberInfo memberInfo,
                                                 HttpSession session) {
        // MemberInfo가 null인지 확인
        if (memberInfo == null) {
            return ResponseEntity.badRequest().body("로그인된 사용자 정보가 없습니다.");
        }

        // 세션에서 다른 필요한 데이터 가져오기
        Languages language = (Languages) session.getAttribute("selectedLanguage");
        Level level = (Level) session.getAttribute("selectedLevel");
        String dailyLearningGoal = (String) session.getAttribute("dailyLearningGoal");

        // null 체크
        if (language == null || level == null || dailyLearningGoal == null) {
            return ResponseEntity.badRequest().body("필요한 정보가 세션에 없습니다.");
        }

        // SettingInfo 업데이트 또는 생성
        SettingInfo settingInfo = new SettingInfo();
        settingInfo.setMemberInfo(memberInfo);
        settingInfo.setAlarm(settingInfoDto.isAlarm());

        // DB에 저장 (merge 사용)
        settingInfoService.saveSettingInfo(settingInfo); // persist 대신 merge를 사용할 수 있음

        // StudySettingStatus 업데이트 또는 생성
        StudySettingStatus studySettingStatus = new StudySettingStatus();
        studySettingStatus.setMemberInfo(memberInfo); // memberInfo 설정
        studySettingStatus.setLanguage(language);
        studySettingStatus.setDailyLearningGoal(DailyLearningGoal.valueOf(dailyLearningGoal));
        studySettingStatus.setLevel(level);

        // DB에 저장 (merge 사용)
        studySettingStatusService.saveStudySettingStatus(studySettingStatus); // persist 대신 merge 사용

        return ResponseEntity.ok("설정이 저장되었습니다.");
    }

}