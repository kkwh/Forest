package com.example.forest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Builder
@Getter
@ToString
@Entity
@Table(name = "BOARD")
@SequenceGenerator(name = "BOARD_SEQ_GEN", sequenceName = "BOARD_SEQ", allocationSize = 1)
public class Board extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(generator = "BOARD_SEQ_GEN", strategy = GenerationType.SEQUENCE)
	private long id;
	
	// 게시판 카테고리
	@Enumerated(EnumType.STRING)
	private BoardCategory boardCategory;
	
	// 게시판 이름
	private String boardName;
	
	// 게시판 등급(메인, 서브)
	private String boardGrade;
	
	// 관리자 승인 여부 (1 -> 승인 완료, 0 -> 승인 대기)
	private int isApproved;
	
	// 게시판 생성자
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	public void setUser(User user) {
		this.user = user;
	}

}
