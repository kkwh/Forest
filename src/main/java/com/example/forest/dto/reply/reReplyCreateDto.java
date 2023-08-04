package com.example.forest.dto.reply;

import lombok.Data;

@Data
public class reReplyCreateDto {

    private Long replyId;
    private Long userId;
    private String reReplyText;
    private String reReplyNickname;
    private String reReplyPassword;
    private String reReplyIp;
    
}
