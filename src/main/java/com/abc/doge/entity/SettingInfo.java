package com.abc.doge.entity;

import com.abc.doge.enums.SystemLang;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "setting_info")
public class SettingInfo {
    // 유저 id PK 이자 FK
    @Id
    private Long memberId;

    // 시스템 언어
    @Enumerated(EnumType.STRING) // ENUM을 STRING으로 저장
    @Column(name = "system_lang", nullable = false)
    private SystemLang systemLang = SystemLang.한국어; // 기본값 설정

    // 알람 유무
    @Column(nullable = false)
    private boolean alarm = true;

    // 다크 모드 유무
    @Column(nullable = false)
    private boolean darkmode = false;

    // 프로필 이미지 파일 이름
    @Column(name = "profile_img", length = 255)
    private String profileImg;

    // 사용자는 하나의 설정 정보만을 가질 수 있음
    //  LAZY 로딩을 사용하면 실제로 해당 엔티티가 필요할 때만 로드
    @OneToOne(fetch = FetchType.LAZY)
    // SettingInfo의 기본키(memberId)가 MemberInfo의 기본키와 동일하다
    @MapsId
    // SettingInfo 테이블에서 member_id라는 FK 컬럼을 정의
    @JoinColumn(name = "member_id")
    private MemberInfo memberInfo;

    // 버전 관리 추가
//    @Version
//    private Integer version;
}
