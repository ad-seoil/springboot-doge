package com.abc.doge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionPageController {

    // Questionpage.html 매핑
    @GetMapping("/Questionpage")
    public String showQuestionPage() {
        return "Questionpage"; // src/main/resources/templates/Questionpage.html 반환
    }
    // Questionpage2.html 매핑
    @GetMapping("/Questionpage2")
    public String showQuestionPage2() {
        return "Questionpage2"; // src/main/resources/templates/Questionpage2.html 반환
    }
    // Questionpage3.html 매핑
    @GetMapping("/Questionpage3")
    public String showQuestionPage3() {
        return "Questionpage3"; // src/main/resources/templates/Questionpage3.html 반환
    }
    // Questionpage4.html 매핑
    @GetMapping("/Questionpage4")
    public String showQuestionPage4() {
        return "Questionpage4"; // src/main/resources/templates/Questionpage4.html 반환
    }

}
