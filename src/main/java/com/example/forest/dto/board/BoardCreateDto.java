package com.example.forest.dto.board;

import org.springframework.web.multipart.MultipartFile;

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
	
	private MultipartFile imageFile;
	
	public Board toEntity() {
		return Board.builder()
				.boardCategory(category)
				.boardName(boardName)
				.boardGrade("Sub")
				.isApproved(0)
				.build();
	}

}
