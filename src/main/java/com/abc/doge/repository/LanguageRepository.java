package com.abc.doge.repository;

import com.abc.doge.entity.Languages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Languages, Integer> {
}

