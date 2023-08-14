package com.example.forest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.forest.dto.chat.ChatRoomDto;
import com.example.forest.model.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
	
	@Query("select new com.example.forest.dto.chat.ChatRoomDto(cr.id, cr.name, u as creator, cr.modifiedTime) "
			+ " from ChatRoom cr, User u "
			+ " where cr.creatorId = u.id "
			+ " order by cr.modifiedTime desc")
	List<ChatRoomDto> findAllRooms();
	
	@Query("select new com.example.forest.dto.chat.ChatRoomDto(cr.id, cr.name, u as creator, cr.modifiedTime) "
			+ " from ChatRoom cr, User u "
			+ " where cr.creatorId = u.id "
			+ " and lower(cr.name) LIKE lower('%' || :keyword || '%') "
			+ " order by cr.modifiedTime desc")
	List<ChatRoomDto> findAllRoomsByKeyword(@Param("keyword") String keyword);

}
