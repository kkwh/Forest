package com.example.forest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.board.BoardRankDto;
import com.example.forest.model.Board;
import com.example.forest.model.BoardCategory;
import com.example.forest.model.Post;
import com.example.forest.model.ReReply;
import com.example.forest.model.Reply;
import com.example.forest.model.User;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	/**
	 * 해당 이름을 게시판이 이미 존재하는지 확인
	 * @param boardName
	 * @return
	 */
	Board findByBoardName(@Param("boardName") String boardName);
	
	/**
	 * 승인(대기) 상태의 게시판 목록을 불러옴
	 * 과거순으로 불러옴
	 * @return
	 */
	@Query("select b from Board b "
			+ " where b.isApproved = :status "
			+ " order by b.id desc")
	List<Board> findAllBoardsByStatus(@Param("status") int status);
	
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
			+ " order by b.boardName")
	List<Board> findAllByCategory(@Param("category") BoardCategory category, @Param("grade") String grade);
	
	/**
	 * 모든 랜드들을 카테고리 별로 불러옴
	 * 게시판 이름 오름차순으로 정렬
	 * @param category
	 * @return
	 */
//	@Query("select b from Board b "
//			+ " where b.boardCategory = :category "
//			+ " and b.isApproved = 1 "
//			+ " order by b.boardName")
//	List<Board> findAllByCategory(@Param("category") BoardCategory category);
	
	/**
	 * 해당 키워드가 포함되어 있는 게시판을 불러옴
	 * 게시판 이름 오름차순으로 정렬
	 * @param keyword
	 * @return
	 */
	@Query("select b from Board b "
			+ " where lower(b.boardName) like ('%' || :keyword || '%') "
			+ " and b.isApproved = 1 "
			+ " and b.boardGrade = :boardGrade "
			+ " order by b.boardName")
	List<Board> findAllByKeyword(@Param("keyword") String keyword, @Param("boardGrade") String boardGrade);
	
	/**
	 * 특정 사용자가 관리자 권한을 가지고 있는 게시판 목록을 불러옴
	 * @param user
	 * @return
	 */
	@Query("select b from Board b "
			+ " where b.user = :user "
			+ " and b.isApproved = 1 "
			+ " order by b.id desc")
	List<Board> findAllBoardsByUserOrderByIdDesc(@Param("user") User user);
	
	/**
	 * 게시판 목록을 생성일자 오름차순으로 불러옴
	 * @param user
	 * @return
	 */
	@Query("select b from Board b "
			+ " where b.user = :user "
			+ " and lower(b.boardName) like ('%' || :keyword || '%')"
			+ " and b.boardCategory in (:categories)"
			+ " and b.isApproved = 1 "
			+ " order by b.createdTime")
	List<Board> findAllByOrderByCreatedTime(@Param("user") User user, @Param("keyword") String keyword, @Param("categories") List<BoardCategory> categories);
	
	/**
	 * 게시판 목록을 생성일자 내림차순으로 불러옴
	 * @param user
	 * @return
	 */
	@Query("select b from Board b "
			+ " where b.user = :user "
			+ " and lower(b.boardName) like ('%' || :keyword || '%')"
			+ " and b.boardCategory in (:categories)"
			+ " and b.isApproved = 1 "
			+ " order by b.createdTime desc")
	List<Board> findAllByOrderByCreatedTimeDesc(@Param("user") User user, @Param("keyword") String keyword, @Param("categories") List<BoardCategory> categories);
	
	/**
	 * 게시판 목록을 이름 오름차순으로 불러옴
	 * @param user
	 * @return
	 */
	@Query("select b from Board b "
			+ " where b.user = :user "
			+ " and lower(b.boardName) like ('%' || :keyword || '%')"
			+ " and b.boardCategory in (:categories)"
			+ " and b.isApproved = 1 "
			+ " order by b.boardName")
	List<Board> findAllByOrderByBoardName(@Param("user") User user, @Param("keyword") String keyword, @Param("categories") List<BoardCategory> categories);
	
	/**
	 * 게시판 목록을 이름 내림차순으로 불러옴
	 * @param user
	 * @return
	 */
	@Query("select b from Board b "
			+ " where b.user = :user "
			+ " and lower(b.boardName) like ('%' || :keyword || '%')"
			+ " and b.boardCategory in (:categories)"
			+ " and b.isApproved = 1 "
			+ " order by b.boardName desc")
	List<Board> findAllByOrderByBoardNameDesc(@Param("user") User user, @Param("keyword") String keyword, @Param("categories") List<BoardCategory> categories);
	
	/**
	 * 게시판 목록을 생성일자 오름차순으로 불러옴
	 * @param user
	 * @return
	 */
	@Query("select b from Board b "
			+ " where lower(b.boardName) like ('%' || :keyword || '%')"
			+ " and b.boardCategory in (:categories)"
			+ " and b.isApproved = :status "
			+ " order by b.createdTime")
	List<Board> findAllByOrderByCreatedTime(@Param("keyword") String keyword, @Param("categories") List<BoardCategory> categories, @Param("status") int status);
	
	/**
	 * 게시판 목록을 생성일자 내림차순으로 불러옴
	 * @param user
	 * @return
	 */
	@Query("select b from Board b "
			+ " where lower(b.boardName) like ('%' || :keyword || '%')"
			+ " and b.boardCategory in (:categories)"
			+ " and b.isApproved = :status "
			+ " order by b.createdTime desc")
	List<Board> findAllByOrderByCreatedTimeDesc(@Param("keyword") String keyword, @Param("categories") List<BoardCategory> categories, @Param("status") int status);
	
	/**
	 * 게시판 목록을 이름 오름차순으로 불러옴
	 * @param user
	 * @return
	 */
	@Query("select b from Board b "
			+ " where lower(b.boardName) like ('%' || :keyword || '%')"
			+ " and b.boardCategory in (:categories)"
			+ " and b.isApproved = :status "
			+ " order by b.boardName")
	List<Board> findAllByOrderByBoardName(@Param("keyword") String keyword, @Param("categories") List<BoardCategory> categories, @Param("status") int status);
	
	/**
	 * 게시판 목록을 이름 내림차순으로 불러옴
	 * @param user
	 * @return
	 */
	@Query("select b from Board b "
			+ " where lower(b.boardName) like ('%' || :keyword || '%')"
			+ " and b.boardCategory in (:categories)"
			+ " and b.isApproved = :status "
			+ " order by b.boardName desc")
	List<Board> findAllByOrderByBoardNameDesc(@Param("keyword") String keyword, @Param("categories") List<BoardCategory> categories, @Param("status") int status);
	
	
	/**
	 * 특정 게시판에 작성된 게시글 수를 가져옴
	 * @param boardId
	 * @return
	 */
	@Query("select count(p.id) "
			+ " from Post p, Board b "
			+ " where b = p.board "
			+ " and b.isApproved = 1 "
			+ " and b.id = :boardId")
	int countPostsByBoardId(@Param("boardId") long boardId);
	
	/**
	 * 관리자가 게시판의 등급을 메인 랜드로 업데이트
	 * @param boardId
	 * @param grade
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update Board b "
			+ " set b.boardGrade = :grade "
			+ " where b.id = :boardId")
	int updateBoardGrade(@Param("boardId") long boardId, @Param("grade") String grade);
	
	/**
	 * 관리자가 신청된 게시판을 승인
	 * @param boardId
	 * @param status
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update Board b "
			+ " set b.isApproved = :status "
			+ " where b.id = :boardId")
	int approveBoard(@Param("boardId") long boardId, @Param("status") int status);
	
	/**
	 * 게시판을 생성한 사용자의 권한을 뺏음
	 * @param user
	 * @param boardId
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update Board b "
			+ " set b.user = :user "
			+ " where b.id = :boardId")
	int updateBoardOwner(@Param("user") User user, @Param("boardId") long boardId);
	
	/**
	 * 랜드의 소개 정보를 변경
	 * @param boardInfo
	 * @return
	 */
	@Transactional
	@Modifying
	@Query("update Board b "
			+ " set b.boardInfo = :boardInfo "
			+ " where b.id = :id")
	int updateBoardInfo(@Param("boardInfo") String boardInfo, @Param("id") long id);
	
	/**
	 * 특정 게시판에 작성된 모든 게시글을 불러옴
	 * @param board
	 * @return
	 */
	@Query("select p from Post p "
			+ " where p.board = :board")
	List<Post> findAllPostsByBoard(@Param("board") Board board);
	
	/**
	 * 
	 * @param post
	 * @return
	 */
	@Query("select r from Reply r "
			+ " where r.post = :post")
	List<Reply> findAllRepliesByPost(@Param("post") Post post);
	
	/**
	 * 
	 * @param post
	 * @return
	 */
	@Query("select r from ReReply r "
			+ " where r.reply = :reply")
	List<ReReply> findAllReRepliesByReply(@Param("reply") Reply reply);
	
	/**
	 * 게시글 수를 기준으로 상위 10개 랜드를 불러옴
	 * @param grade
	 * @return
	 */
	@Query("SELECT new com.example.forest.dto.board.BoardRankDto"
			+ " (p.board.id as id, p.board.boardName AS boardName, ROW_NUMBER() OVER (ORDER BY COUNT(p.id) DESC) AS boardRank, COUNT(p.id) AS postCount) "
			+ " FROM Post p JOIN p.board b "
		    + " WHERE b.boardGrade = :grade "
		    + " GROUP BY p.board.id, p.board.boardName")
	List<BoardRankDto> findTop10Boards(@Param("grade") String grade);
	
	/**
	 * 메인/서브 랜드에 있는 게시판을 생성 시간 내림차순으로 불러옴
	 * @param boardGrade
	 * @return
	 */
	@Query("select b from Board b "
			+ " where b.boardGrade = :boardGrade "
			+ " and b.isApproved = 1 "
			+ " order by b.id desc")
	List<Board> findAllByBoardGradeOrderByCreatedTimeDesc(@Param("boardGrade") String boardGrade);

}
