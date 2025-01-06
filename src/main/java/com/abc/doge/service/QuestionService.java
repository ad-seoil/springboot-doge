package com.abc.doge.service;

import com.abc.doge.entity.Question;
import com.abc.doge.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    // 설정 난이도의 문제를 무작위로 가져오는 메서드
    public List<Question> getRandomQuestionsByDId(int difficultyId, int questionCount) {
        List<Question> questions = questionRepository.findByDifficultyId(difficultyId);

        return questions.subList(0, Math.min(questionCount, questions.size()));
    }

    // ID로 문제를 찾는 메서드
    public Question getQuestionById(int id) {
        return questionRepository.findById((long) id).orElse(null);
    }
}
