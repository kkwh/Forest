package com.example.forest.dto.post;

import lombok.Data;

@Data
public class LikeRequest { // 1일 좋아요 싫어요 초기화    
    
    private Long userId;
    private Long postId;
    private int likeDislike;
    
    
}
