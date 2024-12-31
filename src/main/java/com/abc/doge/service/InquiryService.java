package com.abc.doge.service;

import com.abc.doge.dto.InquiryDto;
import com.abc.doge.entity.InquiryEntity;
import com.abc.doge.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class InquiryService {

// 문의 메일 관련 리파지토리
    private final InquiryRepository inquiryRepository;

    public void send(InquiryDto dto) {
        // InquiryEntity 생성 및 저장
        InquiryEntity entity = InquiryEntity.builder()
                .inqTitle(dto.getInqTitle())
                .inqContent(dto.getInqContent())
                .inqEmail(dto.getInqEmail())
                .build();

        inquiryRepository.save(entity);

//        sendEmail(dto);
    }

}
