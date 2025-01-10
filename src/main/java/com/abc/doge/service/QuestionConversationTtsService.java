package com.abc.doge.service;

import com.abc.doge.entity.QuestionConversationTts;
import com.abc.doge.repository.QuestionConversationTtsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionConversationTtsService {

    @Autowired
    private QuestionConversationTtsRepository qCTRepository;

    public List<QuestionConversationTts> getRandomQuestionsByDId(int dId, int questionCount) {
        List<QuestionConversationTts> questions = qCTRepository.findByDId(dId);
        return questions.subList(0, Math.min(questionCount, questions.size()));
    }
}
