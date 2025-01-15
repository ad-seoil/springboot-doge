package com.abc.doge.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class ImageController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/profile2")
    public String profileForm() {
        return "profileImg";
    }

    @PostMapping("/upload2")
    public String uploadFile(@RequestParam("profileImg")MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "파일이 비어있습니다");
            return "profileImg";
        }

        try {
            File destFile = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(destFile);
            model.addAttribute("message", "파일 엄로드 성공 : " + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace(); // 예외 메시지를 콘솔에 출력
            model.addAttribute("message", "파일 업로드 실패 : " + file.getOriginalFilename());
        }
        return "profileImg";
    }

}
