package com.example.forest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.forest.model.BlackList;
import com.example.forest.model.Role;
import com.example.forest.model.User;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {
	
	/**
	 * 특정 게시판에 등록되어 있는 사용자 블랙 리스트를 불러옴
	 * @param boardId
	 * @return
	 */
	@Query("select b from BlackList b where b.boardId = :boardId")
	List<BlackList> findAllByBoardId(@Param("boardId") long boardId);
	
	@Query("select u "
			+ " from User u "
			+ " where u.id not in (select b.id from BlackList b where b.boardId = :boardId)"
			+ " and u.role != :role"
			+ " and u.id != :userId"
			+ " order by u.nickname")
	List<User> findAllUserNotInList(@Param("boardId") long boardId, @Param("userId") long userId, @Param("role") Role role);

}
