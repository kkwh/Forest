package com.example.forest.dto.post;


import com.example.forest.model.Post;

import lombok.Data;

@Data
public class PostCreateDto {
    
    private String postNickname;
    private String postPassword;
    private String postType;
    private String postTitle;
    private String postContent;
    private long boardId;
    
    // DTO를 엔터티 객체로 변환해서 리턴하는 메서드:
    public Post toEntity() {
        return Post.builder()
                .postNickname(postNickname)
                .postPassword(postPassword)
                .postType(postType)
                .postTitle(postTitle)
                .postContent(postContent)
                .build();
    }
}
