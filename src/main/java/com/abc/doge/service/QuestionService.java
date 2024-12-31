package com.abc.doge.service;

import com.abc.doge.entity.Question;
import com.abc.doge.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getQuestions(int difficultyId, int count) {
        Pageable pageable = PageRequest.of(0, count); // 첫 페이지에서 count만큼의 질문을 가져옵니다.
        return questionRepository.findTopByDifficultyId(difficultyId, pageable); // 수정된 메서드 호출
    }

    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElse(null); // ID로 문제 가져오기
    }
}
