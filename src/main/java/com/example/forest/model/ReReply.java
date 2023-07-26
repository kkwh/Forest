package com.example.forest.model;

import jakarta.persistence.Entity;
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
@Table(name = "RE_REPLIES")
@SequenceGenerator(name = "RE_REPLIES_SEQ_GEN", sequenceName = "RE_REPLIES_SEQ", allocationSize = 1)
public class ReReply extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(generator = "RE_REPLIES_SEQ_GEN", strategy = GenerationType.SEQUENCE)
	private long id;
	
	// 답글 내용
	private String content;
	
	// 답글 작성자 닉네임
	private String nickname;
	
	// 익명 여부
	private boolean isAnonymous;
	
	// 답글이 작성된 댓글
	@ManyToOne
	private Reply reply;

}
