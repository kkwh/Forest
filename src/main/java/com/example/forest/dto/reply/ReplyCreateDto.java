package com.example.forest.dto.reply;

import lombok.Data;

@Data
public class ReplyCreateDto {

    private Long postId;
    private Long userId;
    private String replyText;
    private String replyNickname;
    private String replyPassword;
    private String replyIp;
    
    
    
}
