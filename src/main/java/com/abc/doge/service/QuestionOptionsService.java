package com.abc.doge.service;

import com.abc.doge.repository.QuestionOptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionOptionsService {
    @Autowired
    private QuestionOptionsRepository questionOptionsRepository;
}
