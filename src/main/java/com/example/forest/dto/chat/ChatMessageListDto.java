package com.example.forest.dto.chat;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageListDto {
	
	private long roomId;
	private String loginId;
	private String message;
	private String nickname;
	private LocalDateTime createdTime;

}
