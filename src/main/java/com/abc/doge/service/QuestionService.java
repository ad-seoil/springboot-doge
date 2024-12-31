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
        return questions.subList(0, Math.min(questionCount, questions.size())); // 요청한 갯수만큼 반환
    }
}
