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
@Builder
@Getter
@Entity
@Table(name = "CHAT_ROOMS")
@SequenceGenerator(name = "CHAT_ROOMS_SEQ_GEN", sequenceName = "CHAT_ROOMS_SEQ", allocationSize = 1)
public class ChatRoom extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(generator = "CHAT_ROOMS_SEQ_GEN", strategy = GenerationType.SEQUENCE)
	private long id;
	
	private String name; // 채팅방 이름
	
	private long creatorId; // 채팅방 생성자 아이디

}
