package com.abc.doge.service;

import com.abc.doge.entity.Questions;
import com.abc.doge.enums.QuestionLevel;
import com.abc.doge.enums.QuestionType;
import com.abc.doge.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionsService {

    @Autowired
    private QuestionsRepository questionRepository;

    public List<Questions> getQuestionsByQuestionTypeAndQuestionLevel(QuestionType questionType, QuestionLevel questionLevel) {
        return questionRepository.findByQuestionTypeAndQuestionLevel(questionType, questionLevel);
    }
}