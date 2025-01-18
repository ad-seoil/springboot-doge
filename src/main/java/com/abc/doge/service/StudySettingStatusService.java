package com.abc.doge.service;

import com.abc.doge.entity.StudySettingStatus;
import com.abc.doge.repository.StudySettingStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudySettingStatusService {

    @Autowired
    private StudySettingStatusRepository studySettingStatusRepository; // 리포지토리 주입

    @Transactional
    public void saveStudySettingStatus(StudySettingStatus studySettingStatus) {
        // MemberInfo가 detached 상태일 경우 merge 사용
        studySettingStatusRepository.save(studySettingStatus); // persist 대신 merge 사용
    }

    // 특정 memberId로 학습 설정 상태 조회
    public StudySettingStatus getStudyStatus(Long memberId) {
        return studySettingStatusRepository.findByMemberInfo_MemberId(memberId); // 리포지토리 메서드 호출
    }

}
