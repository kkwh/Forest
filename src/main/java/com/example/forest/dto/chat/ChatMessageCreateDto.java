package com.example.forest.dto.chat;

import lombok.Data;

@Data
public class ChatMessageCreateDto {
	
	private long roomId;
	private String loginId;
	private String message;

}
