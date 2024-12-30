package com.abc.doge.service;

import com.abc.doge.entity.MemberInfo;
import com.abc.doge.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    // 회원가입 메서드
    public void registerMember(MemberInfo member) {
        memberRepository.save(member); // 회원 정보 저장
    }

    public MemberInfo findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
