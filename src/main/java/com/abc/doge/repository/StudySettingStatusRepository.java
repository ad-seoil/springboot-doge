package com.abc.doge.repository;

import com.abc.doge.entity.StudySettingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudySettingStatusRepository extends JpaRepository<StudySettingStatus, Long> {
    // memberId로 StudySettingStatus를 조회하는 메서드
    StudySettingStatus findByMemberInfo_MemberId(Long memberId);
}
