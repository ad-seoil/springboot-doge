package com.abc.doge.service;

import com.abc.doge.entity.Questions;
import com.abc.doge.entity.Questions;
import com.abc.doge.enums.QuestionLevel;
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
    public List<Questions> getRandomQuestionsByLevel(QuestionLevel questionLevel, int questionCount) {
        List<Questions> questions = questionRepository.findByQuestionLevel(questionLevel);

        return questions.subList(0, Math.min(questionCount, questions.size()));
    }

    // ID로 문제를 찾는 메서드
    public Questions getQuestionById(int id) {
        return questionRepository.findById((long) id).orElse(null);
    }
}
