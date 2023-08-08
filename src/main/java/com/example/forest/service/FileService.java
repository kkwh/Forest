package com.example.forest.service;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.forest.dto.file.ImageFileCreateDto;
import com.example.forest.model.ImageFile;
import com.example.forest.repository.ImageFileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {
	
	//private static final String UPLOAD_PATH = "/Users/apple/git/Forest/src/main/resources/static/img/board_profile";
	//private static final String UPLOAD_PATH = "C:\\Users\\ITWILL\\git\\Forest\\src\\main\\resources\\static\\img\\board_profile";
	private static final String UPLOAD_PATH = "C:\\Users\\User\\git\\Forest\\src\\main\\resources\\static\\img\\board_profile"; 
	
	private final ImageFileRepository fileRepository;
	
	/**
	 * 게시판 프로필 이미지를 지정된 폴더와 DB에 저장하는 메서드
	 * @param imageFile
	 * @param boardId
	 */
	public void saveBoardProfileImage(MultipartFile imageFile, long boardId) {
		File file = new File(UPLOAD_PATH);
		
		log.info("file = {}", file);
		
		if(!file.exists()) {
			file.mkdirs();
		}
		
		String originalName = imageFile.getOriginalFilename();
		String ext = imageFile.getContentType();
		String uuid = UUID.randomUUID().toString();
		String fileName = uuid + "_" + originalName;
		String uploadPath = "/images/" + fileName;
		
		log.info("path = {}", uploadPath);
		
		File uploadFile = new File(file, fileName);
		
		log.info("uploadFile = {}", uploadFile);
		
		try {
			imageFile.transferTo(uploadFile);
			
			ImageFileCreateDto dto = ImageFileCreateDto.builder()
					.originalName(originalName)
					.ext(ext)
					.uuid(uuid)
					.uploadPath(uploadPath)
					.fileName(fileName)
					.boardId(boardId)
					.build();
			
			fileRepository.save(dto.toEntity(dto));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 게시판의 프로필 사진을 삭제하는 메서드
	 * @param entity
	 */
	public void deleteImage(ImageFile entity) {
		File uploadPath = new File(UPLOAD_PATH, entity.getFileName());
		
		log.info("deleteFile = {}", uploadPath);
		
		fileRepository.delete(entity);
		
		uploadPath.delete();
	}

}
