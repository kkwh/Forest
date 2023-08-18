package com.example.forest.dto.post;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostWithLikesCount2 { // 인기글 조회 + 좋아요 수 (read-popular.html)
    private Long id;
    private String postType;
    private String postTitle;
    private String postNickname;
    private LocalDateTime createdTime;
    private int postViews;
    private String postIp;
    private long likesCount;    // 좋아요 수
    private long likesDifference;   // 좋아요 수 - 싫어요 수
    private long replyCount; // 댓글 수
    private Long userId;
    private String userLoginId;

    public PostWithLikesCount2(Long id, String postType, String postTitle, String postNickname,
        LocalDateTime createdTime, int postViews, String postIp, long likesCount, long likesDifference, long replyCount, Long userId, String userLoginId) {
        this.id = id;
        this.postType = postType;
        this.postTitle = postTitle;
        this.postNickname = postNickname;
        this.createdTime = createdTime;
        this.postViews = postViews;
        this.postIp = postIp;
        this.likesCount = likesCount;
        this.likesDifference = likesDifference;
        this.replyCount = replyCount;
        this.userId = (userId != null) ? userId : 0; // null 일 때 0
        this.userLoginId = (userLoginId != null) ? userLoginId : "null"; // null 일 때 문자열 null
    }
}