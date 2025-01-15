package com.abc.doge.service;

import com.abc.doge.entity.MemberInfo;
import com.abc.doge.repository.MemberInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberInfoService {

    @Autowired
    private MemberInfoRepository memberInfoRepository;

    public MemberInfo findById(Long memberId) {
        return memberInfoRepository.findById(memberId).orElse(null);
    }
}
