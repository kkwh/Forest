package com.example.forest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.chat.ChatMessageDto;
import com.example.forest.dto.chat.ChatMessageListDto;
import com.example.forest.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

	@Transactional
	@Query("select m from ChatMessage m  "
			+ " where m.roomId = :roomId "
			+ " order by m.id")
	List<ChatMessage> getAllByRoomId(@Param("roomId") long roomId);
	
	@Transactional
	@Query("select new com.example.forest.dto.chat.ChatMessageListDto( "
			+ " m.roomId, u.loginId, m.message, m.nickname, m.createdTime) "
			+ " from ChatMessage m, User u "
			+ " where m.roomId = :roomId "
			+ " and u.nickname = m.nickname "
			+ " order by m.id")
	List<ChatMessageListDto> getAllByChatRoomId(@Param("roomId") long roomId);
	
	@Transactional
	@Modifying
	@Query("update ChatMessage cm "
			+ " set cm.nickname = :newNickname"
			+ " where cm.nickname = :currNickname")
	 void updateUserNickname(@Param("currNickname") String currNickname, @Param("newNickname") String newNickname);
	
}
