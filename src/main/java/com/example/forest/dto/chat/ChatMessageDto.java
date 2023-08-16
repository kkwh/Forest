package com.example.forest.dto.chat;

import com.example.forest.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
	
	private long id;
	private String content;
	private User sender;
	private String createdTime;

}
