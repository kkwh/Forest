package com.example.forest.model;

import groovy.transform.ToString;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    private long id; //다이어리의 아이디.
    
    // 방명록 작성자 아이디(PK 값)
    private long writerId;
    
    // 작성 내용
    private String content;
    
    // 방명록이 달린 블로그
    @ManyToOne(fetch = FetchType.EAGER)
    private Blog blog;
    
    // 방명록 주인 (회원가입 시 생성되는 유저의 ID 값)
//    private long ownerId;

}
