package com.example.forest.dto.board;

import java.time.LocalDateTime;

import com.example.forest.model.Board;
import com.example.forest.model.BoardCategory;
import com.example.forest.model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardListDto {
	
	private long id;
	private BoardCategory boardCategory;
	private String boardName;
	private String boardGrade;
	private int isApproved;
	private User user;
	private LocalDateTime createdTime;
	
	public static BoardListDto fromEntity(Board entity) {
		return BoardListDto.builder()
				.id(entity.getId())
				.boardCategory(entity.getBoardCategory())
				.boardName(entity.getBoardName())
				.boardGrade(entity.getBoardGrade())
				.isApproved(entity.getIsApproved())
				.user(entity.getUser())
				.createdTime(entity.getCreatedTime())
				.build();
	}

}
