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
	@Query("select m from ChatMessage m  "
			+ " where m.roomId = :roomId "
			+ " order by m.id")
	List<ChatMessage> getAllByRoomId(@Param("roomId") long roomId);
	
}
