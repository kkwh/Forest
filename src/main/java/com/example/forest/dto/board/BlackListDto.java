package com.example.forest.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlackListDto {
	
	private long id;
	private long boardId;
	private long userId;
	private String ipAddress;
	private String nickname;

}
