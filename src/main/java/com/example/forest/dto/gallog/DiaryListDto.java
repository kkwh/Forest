package com.example.forest.dto.gallog;

import com.example.forest.model.Blog;
import com.example.forest.model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiaryListDto {

    private long blogId; // 방명록이 작성된 블로그 아이디
    private String loginId; // 방명록을 작성한 유저의 로그인 아이디
    private String content; //방명록 내용.
    
}
