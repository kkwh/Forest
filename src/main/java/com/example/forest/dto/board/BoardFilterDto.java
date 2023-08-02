package com.example.forest.dto.board;

import java.util.List;

import com.example.forest.model.BoardCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardFilterDto {
	
	private long userId;
	private List<BoardCategory> categoryList;

}
