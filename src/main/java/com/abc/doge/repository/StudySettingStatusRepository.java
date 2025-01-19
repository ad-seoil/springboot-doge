package com.abc.doge.repository;

import com.abc.doge.entity.StudySettingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudySettingStatusRepository extends JpaRepository<StudySettingStatus, Long> {
    // memberId로 StudySettingStatus를 조회하는 메서드
    StudySettingStatus findByMemberInfo_MemberId(Long memberId);

    // memberId와 languageId를 가져와서 유니크를 확인하는 메서드
    boolean existsByMemberInfo_MemberIdAndLanguage_Id(Long memberId, Long languageId);

    // 유저의 id와 언어 id를 동시에 조회
    StudySettingStatus findByMemberInfo_MemberIdAndLanguage_Id(Long memberId, Long languageId);
}
