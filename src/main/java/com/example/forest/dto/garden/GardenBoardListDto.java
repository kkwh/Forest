package com.example.forest.dto.garden;

import com.example.forest.model.BoardCategory;
import com.example.forest.model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GardenBoardListDto {

    long boardId; // 보드아이디
    
    private User user; // 유저아이디와 블로그 테이블 아이디
    
    private BoardCategory category; //카테고리
    
    private String boardName; //보드이름
    private String boardInfo; //보드정보
    private String categoryName; //카테고리 이름
    private String createdTime; //생성 시간.
}
