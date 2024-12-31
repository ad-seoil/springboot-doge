package com.abc.doge.repository;

import com.abc.doge.entity.InquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryEntity, String> {
}
