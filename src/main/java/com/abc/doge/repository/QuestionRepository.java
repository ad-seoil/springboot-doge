package com.abc.doge.repository;

import com.abc.doge.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findTopNByDifficultyId(int difficultyId, int count); // 난이도로 문제 조회
}
