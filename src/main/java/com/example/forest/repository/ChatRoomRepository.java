package com.example.forest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.model.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
	
	@Transactional
	@Query("select r from ChatRoom r order by r.modifiedTime desc")
	List<ChatRoom> findAllRooms();

	@Query("select r from ChatRoom r "
			+ " where lower(r.name) LIKE lower('%' || :keyword || '%') "
			+ " order by r.modifiedTime desc")
	List<ChatRoom> findAllRoomsByKeyword(@Param("keyword") String keyword);

}
