package com.example.forest.dto.board;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardRankListDto {
	
	private List<BoardRankDto> top5List;
	private List<BoardRankDto> top10List;
	
	private List<BoardListDto> list1;
	private List<BoardListDto> list2;

}
