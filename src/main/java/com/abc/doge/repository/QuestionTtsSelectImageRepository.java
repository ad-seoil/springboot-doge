package com.abc.doge.repository;

import com.abc.doge.entity.QuestionTtsSelectImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionTtsSelectImageRepository
    extends JpaRepository<QuestionTtsSelectImage, Long> {

    @Query("SELECT q FROM QuestionTtsSelectImage q WHERE q.d_id = ?1 ORDER BY RAND()")
    List<QuestionTtsSelectImage> findByDId(int dId);
}
