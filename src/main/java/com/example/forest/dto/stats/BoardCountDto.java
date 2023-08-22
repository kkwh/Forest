package com.example.forest.dto.stats;

import com.example.forest.model.BoardCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardCountDto {
	
	private BoardCategory category;
	private long count;

}
