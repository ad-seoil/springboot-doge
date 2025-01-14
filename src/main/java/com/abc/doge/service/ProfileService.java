package com.abc.doge.service;

import com.abc.doge.entity.MemberInfo;
import com.abc.doge.entity.SettingInfo;
import com.abc.doge.repository.MemberRepository;
import com.abc.doge.repository.SettingInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ProfileService {

    @Autowired
    private SettingInfoRepository settingInfoRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 회원 정보 업데이트
    public void updateMember(MemberInfo memberInfo) {
        // DB에 회원 정보 업데이트
        memberRepository.save(memberInfo); // 또는 적절한 업데이트 메서드 사용
    }

    // 프로필 이미지 업로드


}
