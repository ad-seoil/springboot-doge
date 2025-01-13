package com.abc.doge.repository;

import com.abc.doge.entity.LearningResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningResultRepository extends JpaRepository<LearningResults, Long> {
}