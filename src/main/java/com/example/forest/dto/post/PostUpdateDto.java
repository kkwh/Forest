package com.example.forest.dto.post;


import com.example.forest.model.Post;

import lombok.Data;

@Data
public class PostUpdateDto {
    
	private Long id;
    private String postNickname;
    private String postPassword;
    private String postTitle;
    private String postContent;
    private String postType;
    
    // DTO를 엔터티 객체로 변환해서 리턴하는 메서드:
    public Post toEntity() {
        return Post.builder()
        		.id(id)
                .postNickname(postNickname)
                .postPassword(postPassword)
                .postTitle(postTitle)
                .postContent(postContent)
                .build();
    }
}
