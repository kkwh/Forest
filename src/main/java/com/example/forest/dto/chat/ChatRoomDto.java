package com.example.forest.dto.chat;

import com.example.forest.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDto {
	
	private long id;
	private String name;
	private User creator;
	private String modifiedTime;

}
