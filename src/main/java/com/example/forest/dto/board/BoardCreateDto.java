package com.example.forest.dto.board;

import com.example.forest.model.Board;
import com.example.forest.model.BoardCategory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardCreateDto {
	
	private BoardCategory category;
	private String boardName;
	private long userId;
	
	public Board toEntity() {
		return Board.builder()
				.boardCategory(category)
				.boardName(boardName)
				.boardGrade("Sub")
				.isApproved(0)
				.build();
	}

}
