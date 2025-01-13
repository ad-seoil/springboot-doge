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

    // 비밀번호 업데이트 2025.01.12 HSJ
    public void updateMember(MemberInfo memberInfo) {
        // 비밀번호가 변경되었는지 확인
        if (memberInfo.getPassword() != null && !memberInfo.getPassword().isEmpty()) {
            // 비밀번호를 해시 처리하지 않고 그대로 저장
            // 이 부분에서 비밀번호를 직접 설정하는 코드가 필요할 수 있습니다.
            System.out.println("저장할 비밀번호: " + memberInfo.getPassword()); // 로그 추가
        }

        memberRepository.save(memberInfo); // DB에 업데이트
    }
}
