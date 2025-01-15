package com.abc.doge.service;

import com.abc.doge.entity.LearningResults;
import com.abc.doge.repository.LearningResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LearningResultService {

    @Autowired
    private LearningResultRepository learningResultRepository;

    public void saveResult(LearningResults result) {
        learningResultRepository.save(result);
    }
}
