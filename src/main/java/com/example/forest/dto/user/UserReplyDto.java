package com.example.forest.dto.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReplyDto {
    
    private long id; // reply id
    private String replyText;
    private long postId;
    private long boardId;
    private LocalDateTime createdTime;

}
