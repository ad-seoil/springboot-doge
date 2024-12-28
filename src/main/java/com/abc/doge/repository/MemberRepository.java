package com.abc.doge.repository;

import com.abc.doge.entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberInfo, Integer> {
//    Optional<MemberInfo> findByID(String id);       // ID로 회원 검색
//    Optional<MemberInfo> findByEmail(String email); // email로 회원 검색
//
//    // ID로 회원정보 찾기 메서드
//    Optional<MemberInfo> findById(String id);
}
