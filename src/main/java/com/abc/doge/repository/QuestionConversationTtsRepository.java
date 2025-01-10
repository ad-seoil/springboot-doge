package com.abc.doge.repository;

import com.abc.doge.entity.QuestionConversationTts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionConversationTtsRepository
extends JpaRepository<QuestionConversationTts, Long> {
    @Query("SELECT q FROM QuestionConversationTts q WHERE q.dId = ?1 ORDER BY RAND()")
    List<QuestionConversationTts> findByDId(int dId);
}
