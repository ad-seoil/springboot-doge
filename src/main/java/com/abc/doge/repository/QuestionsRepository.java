package com.abc.doge.repository;


import com.abc.doge.entity.Questions;
import com.abc.doge.enums.QuestionLevel;
import com.abc.doge.enums.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long> {
    List<Questions> findByQuestionTypeAndQuestionLevel(
            QuestionType questionType, QuestionLevel questionLevel
    );
}
