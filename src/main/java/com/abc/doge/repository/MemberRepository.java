package com.abc.doge.repository;

import com.abc.doge.entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberInfo, Long> {
    MemberInfo findByEmail(String email);
}
