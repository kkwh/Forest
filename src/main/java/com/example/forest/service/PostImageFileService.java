package com.example.forest.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.forest.model.ImageFile;
import com.example.forest.repository.ImageFileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostImageFileService {
	
	private static final String UPLOAD_PATH = "C:\\Users\\ITWILL\\git\\Forest\\src\\main\\resources\\static\\img\\post_profile";
	//private static final String UPLOAD_PATH = "C:\\Users\\User\\git\\Forest\\src\\main\\resources\\static\\img\\board_profile"; 
	
	@Autowired
	  private ImageFileRepository imageFileRepository;

	  public String uploadImage(MultipartFile file) {
	    log.info("file = {}", file);  
	      
	    String originalName = file.getOriginalFilename();
	    String ext = originalName.substring(originalName.lastIndexOf("."));
	    String uuid = UUID.randomUUID().toString();
	    String fileName = uuid + ext;
	    String uploadPath = UPLOAD_PATH;
	    
	    log.info("uploadPath = {}", uploadPath); 
	    
	    try {
	      file.transferTo(new File(uploadPath + "/" + fileName));
	      
	      ImageFile imageFile = ImageFile.builder()
	          .originalName(originalName)
	          .ext(ext)
	          .uuid(uuid)
	          .fileName(fileName)
	          .uploadPath(uploadPath)
	          .build();
	      
	      imageFileRepository.save(imageFile);
	      
	      return uploadPath + "/" + fileName; // 파일 URL을 반환

	    } catch (IOException e) {
	      e.printStackTrace();
	      return null;
	    }
	  }

}
