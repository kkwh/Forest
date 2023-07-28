package com.example.forest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.forest.model.Board;
import com.example.forest.model.BoardCategory;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	/**
	 * 해당 이름을 게시판이 이미 존재하는지 확인
	 * @param boardName
	 * @return
	 */
	Board findByBoardName(@Param("boardName") String boardName);
	
	/**
	 * 메인(서브) 랜드에 존재하는 모든 랜드들을 카테고리 별로 불러옴
	 * 게시판 이름 오름차순으로 정렬
	 * @param category
	 * @return
	 */
	@Query("select b from Board b "
			+ " where b.boardCategory = :category "
			+ " and b.boardGrade = :grade "
			+ " and b.isApproved = 1 "
			+ " order by b.boardName desc")
	List<Board> findAllByCategory(@Param("category") BoardCategory category, @Param("grade") String grade);
	
	/**
	 * 해당 키워드가 포함되어 있는 게시판을 불러옴
	 * 게시판 이름 오름차순으로 정렬
	 * @param keyword
	 * @return
	 */
	@Query("select b from Board b "
			+ " where lower(b.boardName) like ('%' || :keyword || '%') "
			+ " and b.isApproved = 1"
			+ " order by b.boardName desc")
	List<Board> findAllByKeyword(@Param("keyword") String keyword);

}
