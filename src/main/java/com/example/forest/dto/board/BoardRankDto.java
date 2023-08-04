package com.example.forest.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardRankDto {
	
	private Long id;
	private String boardName;
	private Long boardRank;
	private Long postCount;

}
