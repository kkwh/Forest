package com.example.forest.dto.post;


import com.example.forest.model.Post;

import lombok.Data;

@Data
public class ModifyPasswordCheckDto {
       
    private String password; // 수정 직전 입력할 비밀번호
    private Long postId;
    
    
    // DTO를 엔터티 객체로 변환해서 리턴하는 메서드:
   /* public Post toEntity() {
        return Post.builder()
                .postNickname(postNickname)
                .postPassword(postPassword)
                .postType(postType)
                .postTitle(postTitle)
                .postContent(postContent)
                .build();
    } */
}
