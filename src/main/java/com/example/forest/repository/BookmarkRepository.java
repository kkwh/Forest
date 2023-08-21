package com.example.forest.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.board.BookmarkListDto;
import com.example.forest.model.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	/**
	 * boardId와 userId로 즐겨찾기를 삭제함
	 * @param boardId
	 * @param userId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("delete from Bookmark b "
			+ " where b.boardId = :boardId "
			+ " and b.userId = :userId")
	int deleteByBoardIdAndUserId(@Param("boardId") long boardId, @Param("userId") long userId);
	
	/**
	 * 사용자가 해당 게시판을 즐겨찾기 등록했는지 확인하기 위함
	 * @param boardId
	 * @param userId
	 * @return
	 */
	@Transactional
	@Query("select b "
			+ " from Bookmark b "
			+ " where b.boardId = :boardId "
			+ " and b.userId = :userId")
	Bookmark findByBoardIdAndUserId(@Param("boardId") long boardId, @Param("userId") long userId);
	
	/**
	 * 해당 게시판을 즐겨찾기 등록한 유저의 수를 가져옴
	 * @param boardId
	 * @return
	 */
	@Query("select NVL(count(b.id), 0) "
			+ " from Bookmark b "
			+ " where b.boardId = :boardId")
	long countBookmarkByBoardId(@Param("boardId") long boardId);
	
	/**
	 * 사용자의 즐겨찾기 리스트를 불러옴
	 * @param userId
	 * @param pageable
	 * @return
	 */
	@Query("select new com.example.forest.dto.board.BookmarkListDto(bm.id, b.id AS boardId, b.boardName AS boardName, "
			+ " b.boardCategory AS category, if AS file) "
			+ " from Bookmark bm "
			+ " JOIN Board b ON (bm.boardId = b.id) "
			+ " JOIN ImageFile if ON(b.id = if.boardId) "
			+ " where bm.userId = :userId "
			+ " order by b.boardName")
	Page<BookmarkListDto> findAllByUserId(@Param("userId") long userId, Pageable pageable);
	
}
