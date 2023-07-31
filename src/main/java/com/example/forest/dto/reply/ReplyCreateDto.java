package com.example.forest.dto.reply;

import lombok.Data;

@Data
public class ReplyCreateDto {

    private Long postId;
    private String replyText;
    private String replyNickname;
    private String replyPassword;
    
}
