package com.example.forest.dto.gallog;

import com.example.forest.model.Blog;
import com.example.forest.model.Board;
import com.example.forest.model.BoardCategory;
import com.example.forest.model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GallogBoardDto {
    
    long id; //보드아이디
    
    private User user; //유저 아이디, 닉네임. 
    
   private BoardCategory category; //카테고리
   
   private String boardName; //보드이름
   private String boardInfo; //보드정보
   private String categoryName; //카테고리 이름
   private String createdTime; //생성 시간.
   
    
   
}
