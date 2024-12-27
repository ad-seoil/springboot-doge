package com.abc.doge.service;

import com.abc.doge.dto.MemberLoginDto;
import com.abc.doge.entity.MemberInfo;
import com.abc.doge.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Optional<MemberInfo> loginMember(MemberLoginDto loginDto) {
        // ID로 회원 정보를 검색
        Optional<MemberInfo> member = memberRepository.findById(loginDto.getIdentifier());
        if (member.isPresent() && passwordEncoder.matches(loginDto.getPw(), member.get().getPw())) {
            return member; // 로그인 성공
        }
        return Optional.empty(); // 로그인 실패
    }
}
