package com.abc.doge.service;

import com.abc.doge.entity.SettingInfo;
import com.abc.doge.repository.SettingInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    @Autowired
    private SettingInfoRepository settingInfoRepository; // 리포지토리 주입

    public SettingInfo findByMemberId(Long memberId) {
        return settingInfoRepository.findById(memberId).orElse(null); // ID로 설정 정보 조회
    }

    public SettingInfo saveSettingInfo(SettingInfo settingInfo) {
        return settingInfoRepository.save(settingInfo); // 설정 정보 저장
    }
}
