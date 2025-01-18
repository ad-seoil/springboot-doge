package com.abc.doge.service;

import com.abc.doge.entity.Languages;
import com.abc.doge.entity.StudySettingStatus;
import com.abc.doge.repository.LanguageRepository;
import com.abc.doge.repository.StudySettingStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {

    @Autowired
    private LanguageRepository languageRepository; // 언어 리포지토리

    @Autowired
    private StudySettingStatusRepository studySettingStatusRepository; // 언어 학습 설정 리파지토리

    // 모든 언어 반환
    public List<Languages> findAll() {
        return languageRepository.findAll();
    }

    // 언어 ID로 언어 반환
    public Languages findLanguageById(Long languageId) {
        return languageRepository.findById(languageId).orElse(null);
    }

    // 설문조사 설정 저장
    public void saveStudySetingStatus(StudySettingStatus studySettingStatus) {
        studySettingStatusRepository.save(studySettingStatus);
    }

    // 한 유저가 똑같은 언어를 배우려고 하는지 확인
    public boolean existsByUserAndLanguage(Long memberId, Long languageId) {
        return studySettingStatusRepository.existsByMemberInfo_MemberIdAndLanguage_Id(memberId, languageId);
    }

    // 유저와 언어의 id를 동시에 조회
    public StudySettingStatus findByUserIdAndLanguageId(Long memberId, Long languageId) {
        return studySettingStatusRepository.findByMemberInfo_MemberIdAndLanguage_Id(memberId, languageId);
    }



}