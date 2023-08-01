package com.example.forest.dto.post;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostWithLikesCount { // 전체글 조회 + 좋아요 수
    private Long id;
    private String postType;
    private String postTitle;
    private String postNickname;
    private LocalDateTime createdTime;
    private int postViews;
    private long likesCount;

    public PostWithLikesCount(Long id, String postType, String postTitle, String postNickname,
        LocalDateTime createdTime, int postViews, long likesCount) {
        this.id = id;
        this.postType = postType;
        this.postTitle = postTitle;
        this.postNickname = postNickname;
        this.createdTime = createdTime;
        this.postViews = postViews;
        this.likesCount = likesCount;
    }
}