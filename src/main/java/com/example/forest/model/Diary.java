package com.example.forest.model;

import groovy.transform.ToString;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
@Entity
@Table(name = "DIARY")
@SequenceGenerator(name = "DIARY_SEQ_GEN", sequenceName = "DIARY_SEQ", allocationSize = 1)
public class Diary extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(generator = "DIARY_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    private long id;
    
    // 방명록 작성자 닉네임
    private String nickname;
    
    // 작성 내용
    private String content;
    
    // 방명록 주인 (회원가입 시 생성되는 유저의 ID 값)
    private long userId;

}
