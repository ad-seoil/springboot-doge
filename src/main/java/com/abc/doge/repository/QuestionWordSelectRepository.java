package com.abc.doge.repository;

import com.abc.doge.entity.QuestionWordSelect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionWordSelectRepository extends JpaRepository<QuestionWordSelect, Long> {
    @Query("SELECT q FROM QuestionWordSelect q WHERE q.d_id = ?1 ORDER BY RAND()" )
    List<QuestionWordSelect> findByDId(int dId);
}
