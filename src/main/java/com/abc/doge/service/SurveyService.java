package com.abc.doge.service;

import com.abc.doge.entity.Languages;
import com.abc.doge.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyService {

    @Autowired
    private LanguageRepository languageRepository; // 언어 리포지토리

    public List<Languages> findAll() {
        return languageRepository.findAll(); // 모든 언어 반환
    }
}