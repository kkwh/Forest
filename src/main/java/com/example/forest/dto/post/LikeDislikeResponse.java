package com.example.forest.dto.post;

import lombok.Data;

@Data
public class LikeDislikeResponse { // 계정의 좋아요 / 싫어요 체크 DTO
    
    private boolean exists;
    
    
}
