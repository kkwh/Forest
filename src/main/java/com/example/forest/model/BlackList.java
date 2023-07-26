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
@Table(name = "BLACK_LIST")
@SequenceGenerator(name = "BLACK_LIST_SEQ_GEN", sequenceName = "BLACK_LIST_SEQ", allocationSize = 1)
public class BlackList {
	
	@Id
	@GeneratedValue(generator = "BLACK_LIST_SEQ_GEN", strategy = GenerationType.SEQUENCE)
	private long id;
	
	// 게시판 아이디
	private long boardId;
	
	// 차단한 IP 주소
	private String ipAddress;

}
