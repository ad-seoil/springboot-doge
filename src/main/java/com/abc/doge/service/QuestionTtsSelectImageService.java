package com.abc.doge.service;

import com.abc.doge.entity.QuestionTtsSelectImage;
import com.abc.doge.repository.QuestionTtsSelectImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionTtsSelectImageService {

    @Autowired
    private QuestionTtsSelectImageRepository questionTtsSelectImageRepository;

    public List<QuestionTtsSelectImage> getRandomQuestionsByDId(int dId, int questionCount) {
        List<QuestionTtsSelectImage> questions = questionTtsSelectImageRepository.findByDId(dId);
        return questions.subList(0, Math.min(questionCount, questions.size()));
    }

}
