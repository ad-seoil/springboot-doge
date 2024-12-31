package com.abc.doge.service;

import com.abc.doge.entity.Question;
import com.abc.doge.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    // 난이도로 문제 조회
    public List<Question> getQuestions(int difficultyId, int count) {
        return questionRepository.findTopNByDifficultyId(difficultyId, count);
    }

    // ID로 문제 가져오기
    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElse(null);
    }
}
