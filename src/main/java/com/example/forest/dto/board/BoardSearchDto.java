package com.example.forest.dto.board;

import java.util.List;

import com.example.forest.model.BoardCategory;

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
	private String boardGrade;
	private List<BoardCategory> categoryList;

}
