package com.example.forest.dto.chat;

import com.example.forest.model.ChatRoom;

import lombok.Data;

@Data
public class ChatRoomCreateDto {
	
	private String name;
	private String loginId;
	private long creatorId;

}
