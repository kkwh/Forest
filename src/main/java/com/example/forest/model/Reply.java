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
@ToString
@Getter
@Builder
@Entity
@Table(name = "REPLIES")
@SequenceGenerator(name = "REPLIES_SEQ_GEN", sequenceName = "REPLIES_SEQ", allocationSize = 1)
public class Reply extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(generator = "REPLIES_SEQ_GEN", strategy = GenerationType.SEQUENCE)
	private long id;
	
	// 댓글 내용
	private String replyText;
	
	// 댓글 작성자 닉네임
	private String nickname;
	
	// 익명 여부
	private boolean isAnonymous;
	
	// 댓글 추천수
	private int recommendations;
	
	// 댓글이 작성된 게시물
	@ManyToOne(fetch = FetchType.LAZY)
	private Post post;

}
