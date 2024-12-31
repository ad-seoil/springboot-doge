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

    public List<Question> getQuestionByDifficultyId(int dId, int questionCount) {
        List<Question> questions = questionRepository.findByDifficultyId(dId);

        // 요청 갯수보다 적은 경우 전체 반환
        if (questions.size() < questionCount) {
            return questions;
        }

        return questions.subList(0, questionCount); // 요청한 갯수만큼 반환
    }
}
