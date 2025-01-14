//package com.abc.doge.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Data
//@Entity
//@Table(name = "question_options")
//public class QuestionOptions {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "question_id", nullable = false)
//    private Questions question;
//
//    @Column(nullable = false, length = 255)
//    private String ex1;
//
//    @Column(nullable = false, length = 255)
//    private String ex2;
//
//    @Column(nullable = false, length = 255)
//    private String ex3;
//
//    @Column(nullable = false)
//    private int answer;
//}
