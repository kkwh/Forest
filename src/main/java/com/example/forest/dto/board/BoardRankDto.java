package com.example.forest.dto.board;

import com.example.forest.model.BoardCategory;
import com.example.forest.model.ImageFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardRankDto {
	
	private Long id;
	private String boardName;
	private BoardCategory category;
	private Long boardRank;
	private Long postCount;
	private ImageFile file;

}
