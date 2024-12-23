package com.abc.doge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// 작업하는 controller가 아닌 돌아가는지 확인만 하는 controller
@Controller
public class TestController {

    @GetMapping("/")
    @ResponseBody
    public String test(){
        return "abcd!";
    }

}
