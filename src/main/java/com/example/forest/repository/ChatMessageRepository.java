package com.example.forest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.chat.ChatMessageDto;
import com.example.forest.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

	@Transactional
	@Query("select new com.example.forest.dto.chat.ChatMessageDto(m.id, m.content, u as sender, m.createdTime) "
			+ " from ChatMessage m, User u "
			+ " where m.senderId = u.id "
			+ " and m.roomId = :roomId "
			+ " order by m.id")
	List<ChatMessageDto> getAllByRoomId(@Param("roomId") long roomId);
	
}
