package com.example.forest.dto.post;

import lombok.Data;

@Data
public class LikeDislikeResponse { // 계정의 좋아요 / 싫어요 체크 DTO
    
    private boolean exists;
    
    public LikeDislikeResponse(boolean exists) {
        this.exists = exists;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
    
}
