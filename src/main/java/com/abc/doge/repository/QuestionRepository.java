package com.abc.doge.repository;

import com.abc.doge.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.d_id = ?1 ORDER BY RAND()" )
    List<Question> findByDifficultyId(int dId);
}
