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
@Builder
@Getter
@Entity
@Table(name = "CHAT_MESSAGES")
@SequenceGenerator(name = "CHAT_MESSAGES_SEQ_GEN", sequenceName = "CHAT_MESSAGES_SEQ", allocationSize = 1)
public class ChatMessage extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(generator = "CHAT_MESSAGES_SEQ_GEN", strategy = GenerationType.SEQUENCE)
	private long id;
	private String content; // 메세지 내용
	
	private long senderId; // 채팅 작성자 아이디
	
	private long roomId; // 채팅방 아이디

}
