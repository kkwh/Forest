package com.example.forest.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.forest.dto.file.ImageFileCreateDto;
import com.example.forest.model.ImageFile;
import com.example.forest.repository.ImageFileRepository;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {
	
	private static final String PROJECT_ID = "forest-395401";
	private static final String BUCKET_NAME = "itwill-forest-bucket";
	private static final String UPLOAD_PATH = "https://storage.googleapis.com/itwill-forest-bucket/";
	
	private final ImageFileRepository fileRepository;
	
	/**
	 * 게시판 프로필 이미지를 지정된 폴더와 DB에 저장하는 메서드
	 * @param imageFile
	 * @param boardId
	 * @throws IOException 
	 */
//	public void saveBoardProfileImage(MultipartFile imageFile, long boardId) {
//		File file = new File(UPLOAD_PATH);
//		
//		log.info("file = {}", file);
//		
//		if(!file.exists()) {
//			file.mkdirs();
//		}
//		
//		String originalName = imageFile.getOriginalFilename();
//		String ext = imageFile.getContentType();
//		String uuid = UUID.randomUUID().toString();
//		String fileName = uuid + "_" + originalName;
//		String uploadPath = "/images/" + fileName;
//		
//		log.info("path = {}", uploadPath);
//		
//		File uploadFile = new File(file, fileName);
//		
//		log.info("uploadFile = {}", uploadFile);
//		
//		try {
//			imageFile.transferTo(uploadFile);
//			
//			ImageFileCreateDto dto = ImageFileCreateDto.builder()
//					.originalName(originalName)
//					.ext(ext)
//					.uuid(uuid)
//					.uploadPath(uploadPath)
//					.fileName(fileName)
//					.boardId(boardId)
//					.build();
//			
//			fileRepository.save(dto.toEntity(dto));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	/**
	 * 게시판의 프로필 사진을 삭제하는 메서드
	 * @param entity
	 */
	public void deleteImage(ImageFile entity) {
		String objectName = entity.getUuid();
		
		Storage storage = StorageOptions.newBuilder().setProjectId(PROJECT_ID).build().getService();
	    Blob blob = storage.get(BUCKET_NAME, objectName);
	    if (blob == null) {
	      System.out.println("The object " + objectName + " wasn't found in " + BUCKET_NAME);
	      return;
	    }

	    Storage.BlobSourceOption precondition =
	        Storage.BlobSourceOption.generationMatch(blob.getGeneration());

	    storage.delete(BUCKET_NAME, objectName, precondition);

	    log.info("Object {} was deleted from {}", objectName, BUCKET_NAME);
	    
	    fileRepository.delete(entity);
	}
	
	private final Storage storage;
	
	/**
	 * GCP 클라우드로 이미지 저장하는 메서드
	 * @param imageFile
	 * @param boardId
	 */
	public void saveBoardProfileImage(MultipartFile imageFile, long boardId) {
		String originalName = imageFile.getOriginalFilename();
		String ext = imageFile.getContentType();
		String uuid = UUID.randomUUID().toString();
		String fileName = uuid + "_" + originalName;
		String uploadPath = UPLOAD_PATH + uuid;
		
		BlobInfo imageInfo = BlobInfo.newBuilder(BUCKET_NAME, uuid)
				.setContentType(ext)
				.build();	
		
		BlobInfo blobInfo = null;
		try {
			blobInfo = storage.create(
					imageInfo, imageFile.getInputStream()
			);
			
			ImageFileCreateDto dto = ImageFileCreateDto.builder()
					.originalName(originalName)
					.ext(ext)
					.uuid(uuid)
					.uploadPath(uploadPath)
					.fileName(fileName)
					.boardId(boardId)
					.build();
			
			fileRepository.save(dto.toEntity(dto));
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}

}
