package com.abc.doge.service;

import com.abc.doge.entity.StudySettingStatus;
import com.abc.doge.repository.StudySettingStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudySettingStatusService {

    @Autowired
    private StudySettingStatusRepository studySettingStatusRepository; // 리포지토리 주입

    // 특정 memberId로 학습 설정 상태 조회
    public StudySettingStatus getStudyStatus(Long memberId) {
        return studySettingStatusRepository.findByMemberInfo_MemberId(memberId); // 리포지토리 메서드 호출
    }

    // 추가적인 메서드를 필요에 따라 정의할 수 있습니다.
}
