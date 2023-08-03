package com.example.forest.dto.board;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.example.forest.model.Board;
import com.example.forest.model.BoardCategory;
import com.example.forest.model.ImageFile;
import com.example.forest.model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardListDto {
	
	private long id;
	private BoardCategory category;
	private String boardName;
	private String boardInfo;
	private String boardGrade;
	private String categoryName;
	private int isApproved;
	private User user;
	private String createdTime;
	private ImageFile file;
	
	public static BoardListDto fromEntity(Board entity) {
		return BoardListDto.builder()
				.id(entity.getId())
				.category(entity.getBoardCategory())
				.boardName(entity.getBoardName())
				.boardInfo(entity.getBoardInfo())
				.boardGrade(entity.getBoardGrade())
				.categoryName(entity.getBoardCategory().getValue())
				.isApproved(entity.getIsApproved())
				.user(entity.getUser())
				.createdTime(entity.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.KOREA)))
				.build();
	}

}
