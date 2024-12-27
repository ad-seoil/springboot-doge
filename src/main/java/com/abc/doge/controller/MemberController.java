package com.abc.doge.controller;

import com.abc.doge.dto.MemberLoginDto;
import com.abc.doge.dto.MemberRegistrationDto;
import com.abc.doge.entity.MemberInfo;
import com.abc.doge.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody MemberRegistrationDto memberRegistrationDTO
            ){
        memberService.registerMember(memberRegistrationDTO);
        return ResponseEntity.ok("회원가입을 완료했습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginDto loginDto) {
        Optional<MemberInfo> member = memberService.loginMember(loginDto);
        if (member.isPresent()) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(401).body("로그인 실패: 잘못된 ID 또는 비밀번호");
        }
    }
}
