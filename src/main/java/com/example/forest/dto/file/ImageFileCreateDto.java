package com.example.forest.dto.file;

import com.example.forest.model.ImageFile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageFileCreateDto {
	
	private String originalName;
	private String ext;
	private String uuid;
	private String fileName;
	private String uploadPath;
	private long boardId;
	
	public ImageFile toEntity(ImageFileCreateDto dto) {
		return ImageFile.builder()
				.originalName(dto.getOriginalName())
				.ext(dto.getExt())
				.uuid(dto.getUuid())
				.fileName(dto.getFileName())
				.uploadPath(dto.getUploadPath())
				.boardId(dto.getBoardId())
				.build();
	}

}
