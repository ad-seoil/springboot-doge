package com.abc.doge.service;

import com.abc.doge.entity.QuestionWordSelectVideo;
import com.abc.doge.repository.QuestionWordSelectVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionWordSelectVideoService {

    @Autowired
    private QuestionWordSelectVideoRepository questionWordSelectVideoRepository;

    public List<QuestionWordSelectVideo> getRandomQuestionsByDId(int dId, int questionCount) {
        List<QuestionWordSelectVideo> questions
                = questionWordSelectVideoRepository.findByDId(dId);
        return questions.subList(0, Math.min(questionCount, questions.size()));
    }
}
