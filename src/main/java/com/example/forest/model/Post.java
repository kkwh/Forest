package com.example.forest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@Entity
@Table(name = "POSTS")
@SequenceGenerator(name = "POSTS_SEQ_GEN", sequenceName = "POSTS_SEQ", allocationSize = 1)
public class Post extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(generator = "POSTS_SEQ_GEN", strategy = GenerationType.SEQUENCE)
	private long id;
	
	// 게시글 제목
	private String title;
	
	// 게시글 내용
	private String content;
	
	// 조회수
	private int views;
	
	// 게시글 작성자
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	// 게시판 종류
	@ManyToOne(fetch = FetchType.LAZY)
	private Board board;

}
