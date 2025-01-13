package com.abc.doge.entity;

import com.abc.doge.enums.SystemLang;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "setting_info")
public class SettingInfo {

    @Id
    private Long memberId;

    @Enumerated(EnumType.STRING) // ENUM을 STRING으로 저장
    @Column(name = "system_lang", nullable = false)
    private SystemLang systemLang = SystemLang.한국어; // 기본값 설정

    @Column(nullable = false)
    private boolean alarm;

    @Column(nullable = false)
    private boolean darkmode;

    @Column(name = "profile_img", length = 255)
    private String profileImg;

    @OneToOne
    @MapsId
    @JoinColumn(name = "member_id")
    private MemberInfo memberInfo;
}
