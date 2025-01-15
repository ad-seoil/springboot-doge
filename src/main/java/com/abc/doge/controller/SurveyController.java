package com.abc.doge.controller;

import com.abc.doge.entity.Languages;
import com.abc.doge.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SurveyController {

    @Autowired
    private SurveyService surveyService; // 언어 서비스

    @GetMapping("/survey")
    public String getLanguageSurvey(Model model) {
        List<Languages> languages = surveyService.findAll(); // 모든 언어 가져오기
        model.addAttribute("languages", languages); // 모델에 추가
        return "survey_lang"; // survey.html로 이동
    }
}