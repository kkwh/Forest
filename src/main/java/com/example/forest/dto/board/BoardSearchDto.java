package com.example.forest.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSearchDto {
	
	private long userId;
	private String type;
	private String keyword;

}
