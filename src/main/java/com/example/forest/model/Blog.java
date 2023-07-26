package com.example.forest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@Table(name = "BLOG")
@SequenceGenerator(name = "BLOG_SEQ_GEN", sequenceName = "BLOG_SEQ", allocationSize = 1)
public class Blog extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(generator = "BLOG_SEQ_GEN", strategy = GenerationType.SEQUENCE)
	private long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	private User user;
	
	// 본인 게시물 공개 여부
	private boolean postPrivate;
	
	// 본인 작성 댓글 공개 여부
	private boolean replyPrivate;

}
