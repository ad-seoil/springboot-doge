package com.abc.doge.repository;

import com.abc.doge.entity.QuestionOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionOptionsRepository extends JpaRepository<QuestionOptions, Long> {
    List<QuestionOptions> findByQuestionsId(Long question_id);
}
