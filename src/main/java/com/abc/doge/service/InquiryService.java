package com.abc.doge.service;

import com.abc.doge.dto.InquiryDto;
import com.abc.doge.entity.InquiryEntity;
import com.abc.doge.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class InquiryService {

    @Autowired
    private JavaMailSender mailSender;

    // 문의 메일 관련 리파지토리
    private final InquiryRepository inquiryRepository;

    public void sendEmail(InquiryDto dto) {
        // InquiryEntity 생성 및 저장
        InquiryEntity entity = InquiryEntity.builder()
                .inqTitle(dto.getInqTitle())
                .inqContent(dto.getInqContent())
                .inqEmail(dto.getInqEmail())
                .build();
        inquiryRepository.save(entity);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getInqEmail()); // 수신자 이메일
        message.setSubject(dto.getInqTitle()); // 제목
        message.setText(dto.getInqContent()); // 내용
        mailSender.send(message); // 이메일 전송
    }

}
