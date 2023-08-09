package com.example.forest.model;

import jakarta.persistence.Column;
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
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
@Entity
@Table(name = "REPLIES")
@SequenceGenerator(name = "REPLIES_SEQ_GEN", sequenceName = "REPLIES_SEQ", allocationSize = 1)
public class Reply extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(generator = "REPLIES_SEQ_GEN", strategy = GenerationType.SEQUENCE)
	
	// primary key
	private Long id; 
	
	// 댓글 내용
	@Column(nullable = false) // not null
	private String replyText;
	
	// 댓글 작성자 닉네임
	@Column(nullable = false) // not null
	private String replyNickname;
	
	// 답글 작성자 IP
	//@Column(nullable = false) // not null
	private String replyIp;
	
	// 비밀번호(익명인경우)
	private String replyPassword;
	
	// 로그인 한 사용자 일 경우 userId
	private long userId;
	
	// 댓글이 작성된 게시물
	@ManyToOne(fetch = FetchType.EAGER)
	private Post post;

}
