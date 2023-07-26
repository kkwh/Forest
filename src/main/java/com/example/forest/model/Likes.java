package com.example.forest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Table(name = "LIKES")
@SequenceGenerator(name = "LIKES_SEQ_GEN", sequenceName = "LIKES_SEQ", allocationSize = 1)
public class Likes extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(generator = "LIKES_SEQ_GEN", strategy = GenerationType.SEQUENCE)
	private long id;
	
	// 추천 or 비추천
	private boolean likeDislike;
	
	@OneToOne
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Post post;

}
