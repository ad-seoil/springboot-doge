package com.abc.doge.service;

import com.abc.doge.entity.QuestionWordSelect;
import com.abc.doge.repository.QuestionWordSelectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionWordSelectService {

    @Autowired
    private QuestionWordSelectRepository questionWordSelectRepository;

    // 설정 난이도의 문제를 무작위로 가져오는 메서드
    public List<QuestionWordSelect> getRandomQuestionsByDId(int dId, int questionCount) {
        List<QuestionWordSelect> questions = questionWordSelectRepository.findByDId(dId);

        return questions.subList(0, Math.min(questionCount, questions.size()));
    }

    // ID로 문제를 찾는 메서드
    public QuestionWordSelect getQuestionById(int id) {
        return questionWordSelectRepository.findById((long) id).orElse(null);
    }
}
