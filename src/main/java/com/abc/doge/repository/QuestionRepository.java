package com.abc.doge.repository;

import com.abc.doge.entity.Questions;
import com.abc.doge.enums.QuestionLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Long> {
    List<Questions> findByQuestionLevel(QuestionLevel questionLevel); // ENUM 타입으로 변경
}