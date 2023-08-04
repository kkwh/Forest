package com.example.forest.dto.board;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardModifyDto {
	
	private long boardId;
	private String boardInfo;
	private MultipartFile imageFile;

}
