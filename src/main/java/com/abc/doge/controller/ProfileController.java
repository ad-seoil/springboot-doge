package com.abc.doge.controller;

import com.abc.doge.entity.MemberInfo;
import com.abc.doge.entity.SettingInfo;
import com.abc.doge.entity.StudySettingStatus;
import com.abc.doge.service.MemberService;
import com.abc.doge.service.ProfileService;
import com.abc.doge.service.SettingService;
import com.abc.doge.service.StudySettingStatusService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Controller
public class ProfileController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private StudySettingStatusService studySettingStatusService;

    // 학습 기록 페이지 매핑
    @GetMapping("/learning-records")
    public String showLearningRecordsPage() {
        return "learning_records"; // "src/main/resources/templates/learning_records.html" 반환
    }

    // 설정 페이지 매핑
    @GetMapping("/settings")
    public String showSettingsPage() {
        return "settings"; // "src/main/resources/templates/settings.html" 반환
    }

    @GetMapping("/profile")
    public String getProfile(HttpSession session, Model model) {
        MemberInfo loggedInUser = (MemberInfo) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            System.out.println("Session does not contain loggedInUser. Redirecting to login.");
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        String email = loggedInUser.getEmail();
        System.out.println("Logged in user email: " + email);

        MemberInfo memberInfo = memberService.findByEmail(email);
        if (memberInfo == null) {
            System.out.println("No MemberInfo found for email: " + email);
            return "redirect:/error"; // 오류 페이지
        }

        SettingInfo settingInfo = settingService.findByMemberId(memberInfo.getMemberId());
        if (settingInfo == null) {
            System.out.println("SettingInfo not found for memberId: " + memberInfo.getMemberId());
            settingInfo = new SettingInfo(); // 기본 값 추가
        }

        StudySettingStatus studyStatus = studySettingStatusService.getStudyStatus(memberInfo.getMemberId());
        if (studyStatus == null) {
            System.out.println("StudySettingStatus not found for memberId: " + memberInfo.getMemberId());
            studyStatus = new StudySettingStatus(); // 기본 값 추가
        }

        // 모델에 추가
        model.addAttribute("memberInfo", memberInfo);
        model.addAttribute("settingInfo", settingInfo);
        model.addAttribute("studyStatus", studyStatus);

        return "profile"; // profile.html로 이동
    }




    @Autowired
    private ProfileService profileService;

    // 프로필 정보 업데이트
    @PostMapping("/updateProfile")
    public String updateProfile(@RequestParam String nickname,
                                @RequestParam String email,
                                @RequestParam(required = false) String birthDate,
                                @RequestParam(required = false) String password,
                                HttpSession session,
                                Model model) {
        System.out.println("비밀번호 입력값: " + password); // 로그 추가

        // 세션에서 로그인한 사용자 정보 가져오기
        MemberInfo loggedInUser = (MemberInfo) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        // 사용자 정보를 업데이트
        loggedInUser.setNickname(nickname);
        loggedInUser.setEmail(email);
        if (birthDate != null && !birthDate.isEmpty()) {
            loggedInUser.setBirth(LocalDate.parse(birthDate)); // 생년월일 업데이트
        }

        // 비밀번호가 입력된 경우 업데이트
        if (password != null && !password.isEmpty()) {
            loggedInUser.setPassword(password); // 비밀번호 설정
            System.out.println("업데이트할 비밀번호: " + loggedInUser.getPassword()); // 로그 추가
        }

        // DB에 업데이트 (서비스 메서드를 통해)
        memberService.updateMember(loggedInUser);

        model.addAttribute("message", "프로필이 업데이트되었습니다.");
        return "redirect:/profile"; // 프로필 페이지로 리다이렉트
    }

    // 계정 삭제
    @DeleteMapping("/api/members/delete")
    @ResponseBody
    public ResponseEntity<?> deleteAccount(HttpSession session) {
        // 세션에서 로그인한 사용자 정보 가져오기
        MemberInfo loggedInUser = (MemberInfo) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다."); // 로그인 필요 메시지
        }

        try {
            // 사용자의 ID로 계정 삭제
            memberService.deleteMember(loggedInUser.getMemberId());
            session.invalidate(); // 세션 무효화
            return ResponseEntity.ok("계정이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("계정 삭제 중 오류가 발생했습니다.");
        }
    }

    // 프로필 이미지 업로드
    @PostMapping("/uploadProfileImage")
    public ResponseEntity<?> uploadProfileImage(@RequestParam("file") MultipartFile file, HttpSession session) {
        // 세션에서 로그인한 사용자 정보 가져오기
        MemberInfo loggedInUser = (MemberInfo) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다."); // 로그인 필요 메시지
        }

        // 파일이 비어있지 않은지 확인
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 비어 있습니다.");
        }

        try {
            // 저장할 경로 설정 (resources/static/uploads/profile)
            String userHome = System.getProperty("user.home"); // 사용자 홈 경로 얻기
            String uploadDir = userHome + "/uploads/profile"; // 사용자 홈의 uploads/profile 경로 설정

            // 디렉토리 생성
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs(); // 디렉토리가 없으면 생성
            }

            // 파일 이름 설정
            String fileName = loggedInUser.getMemberId() + "_" + file.getOriginalFilename(); // 사용자 ID를 이용한 고유 파일 이름
            File destinationFile = new File(directory, fileName);
            file.transferTo(destinationFile); // 파일 저장

            // SettingInfo에서 프로필 이미지 이름 업데이트
            SettingInfo settingInfo = settingService.findByMemberId(loggedInUser.getMemberId());
            if (settingInfo != null) {
                settingInfo.setProfileImg(fileName); // 이미지 이름 저장
                settingService.updateSettingInfo(settingInfo); // DB에 업데이트
            }

            return ResponseEntity.ok("프로필 이미지가 성공적으로 업로드되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("파일 업로드 중 오류가 발생했습니다.");
        }
    }

}

