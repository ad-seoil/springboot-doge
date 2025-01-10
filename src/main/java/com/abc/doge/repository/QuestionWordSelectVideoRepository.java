package com.abc.doge.repository;

import com.abc.doge.entity.QuestionWordSelectVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionWordSelectVideoRepository
extends JpaRepository<QuestionWordSelectVideo, Long> {
    @Query("SELECT q FROM QuestionWordSelectVideo q WHERE q.dId = ?1 ORDER BY RAND()")
    List<QuestionWordSelectVideo> findByDId(int dId);
}
