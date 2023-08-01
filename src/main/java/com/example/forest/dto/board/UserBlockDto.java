package com.example.forest.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBlockDto {
	
	private long boardId;
	private long userId;
	private String ipAddr;

}
