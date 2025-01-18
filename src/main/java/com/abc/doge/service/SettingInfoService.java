package com.abc.doge.service;

import com.abc.doge.entity.MemberInfo;
import com.abc.doge.entity.SettingInfo;
import com.abc.doge.repository.MemberInfoRepository;
import com.abc.doge.repository.SettingInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingInfoService {

    @Autowired
    private SettingInfoRepository settingInfoRepository;

    @Autowired
    private MemberInfoRepository memberInfoRepository;

    // 특정 사용자 ID로 SettingInfo를 조회
    public SettingInfo findByMemberId(Long memberId) {
        return settingInfoRepository.findById(memberId)
                .orElseGet(() -> {
                    // 기본값으로 새로운 SettingInfo 생성
                    SettingInfo defaultSettingInfo = new SettingInfo();
                    defaultSettingInfo.setMemberId(memberId); // ID 설정
                    return defaultSettingInfo;
                });
    }

    // SettingInfo 저장
    @Transactional
    public void saveSettingInfo(SettingInfo settingInfo) {
        // MemberInfo를 영속 상태로 가져오기
        MemberInfo persistentMemberInfo = memberInfoRepository.findById(settingInfo.getMemberInfo().getMemberId())
                .orElseThrow(() -> new RuntimeException("MemberInfo not found"));
        settingInfo.setMemberInfo(persistentMemberInfo); // 영속 상태로 설정
        settingInfoRepository.save(settingInfo); // 이제 save 가능
    }

    // 알림 설정을 업데이트하는 메서드
    public void updateAlarmSetting(Long memberId, boolean alarm) {
        SettingInfo settingInfo = findByMemberId(memberId);
        settingInfo.setAlarm(alarm); // 알림 유무 업데이트
        saveSettingInfo(settingInfo); // 업데이트된 객체 저장
    }

    // 다크 모드 설정 업데이트
    public void updateDarkModeSetting(Long memberId, boolean darkmode) {
        SettingInfo settingInfo = findByMemberId(memberId);
        settingInfo.setDarkmode(darkmode); // 다크 모드 유무 업데이트
        saveSettingInfo(settingInfo); // 업데이트된 객체 저장
    }
}