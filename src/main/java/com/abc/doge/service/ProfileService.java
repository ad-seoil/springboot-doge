package com.abc.doge.service;

import com.abc.doge.entity.MemberInfo;
import com.abc.doge.entity.SettingInfo;
import com.abc.doge.repository.MemberRepository;
import com.abc.doge.repository.SettingInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ProfileService {

    @Autowired
    private SettingInfoRepository settingInfoRepository;

    @Autowired
    private MemberRepository memberRepository;

    public String uploadProfileImage(MultipartFile file, Long memberId) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어 있습니다.");
        }

        // 파일을 저장할 경로 설정
        String uploadDir = "static/profileImg/";
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs(); // 경로가 없으면 생성
        }

        // 파일 이름과 경로 설정
        String fileName = file.getOriginalFilename();
        File destinationFile = new File(uploadPath, fileName);
        file.transferTo(destinationFile); // 파일 저장

        // SettingInfo 업데이트
        SettingInfo settingInfo = settingInfoRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("설정 정보를 찾을 수 없습니다."));
        settingInfo.setProfileImg(fileName); // 파일 이름과 형식 저장
        settingInfoRepository.save(settingInfo); // 변경 사항 저장

        return fileName; // 성공적으로 업로드된 파일 이름 반환
    }

    public void updateMember(MemberInfo memberInfo) {
        // DB에 회원 정보 업데이트
        memberRepository.save(memberInfo); // 또는 적절한 업데이트 메서드 사용
    }

}
