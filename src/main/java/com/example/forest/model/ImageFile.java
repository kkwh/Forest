package com.example.forest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
@Entity
@Table(name = "IMAGE_FILES")
@SequenceGenerator(name = "FILES_SEQ_GEN", sequenceName = "IMAGE_FILES_SEQ", allocationSize = 1)
public class ImageFile extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(generator = "FILES_SEQ_GEN", strategy = GenerationType.SEQUENCE)
	private long id;
	
	// 파일 원본 이름
	private String originalName;
	
	// 확장자
	private String ext;
	
	// UUID
	private String uuid;
	
	// 파일 저장 이름 (같은 경로에 동일한 이름의 파일이 여러 개 저장될 경우 이를 구분하기 위한 목적)
	private String fileName;
	
	// 파일 저장 경로
	private String uploadPath;

}
