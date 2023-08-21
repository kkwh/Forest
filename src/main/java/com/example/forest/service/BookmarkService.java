package com.example.forest.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.forest.dto.board.BookmarkCreateDto;
import com.example.forest.dto.board.BookmarkListDto;
import com.example.forest.model.Bookmark;
import com.example.forest.repository.BookmarkRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookmarkService {
	
	private final BookmarkRepository bookmarkRepository;
	
	/**
	 * 유저의 즐겨찾기 목록에 해당 게시판을 추가하는 메서드
	 * @param dto
	 */
	public void add(BookmarkCreateDto dto) {
		log.info("save(dto = {})", dto);
		
		Bookmark entity = Bookmark.builder()
				.boardId(dto.getBoardId())
				.userId(dto.getUserId())
				.build();
		
		bookmarkRepository.save(entity);
	}
	
	/**
	 * 유저의 즐겨찾기 목록에서 해당 게시판을 삭제하는 메서드
	 * @param boardId
	 * @param userId
	 */
	public void delete(long boardId, long userId) {
		log.info("delete(boardId = {}, userId = {})", boardId, userId);
		
		bookmarkRepository.deleteByBoardIdAndUserId(boardId, userId);
	}
	
	/**
	 * Bookmark ID로 삭제하는 메서드
	 * @param id
	 */
	public void delete(long id) {
		log.info("delete(id = {})", id);
		
		bookmarkRepository.deleteById(id);
	}
	
	/**
	 * 유저가 해당 게시판을 즐겨찾기 등록했는지 여부를 확인하는 메서드
	 * @param boardId
	 * @param userId
	 * @return
	 */
	public int isExisting(long boardId, long userId) {
		log.info("isExisting(boardId = {}, userId = {})", boardId, userId);
		
		Bookmark entity = bookmarkRepository.findByBoardIdAndUserId(boardId, userId);
		
		if(entity == null) {
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 게시판을 즐겨찾기 등록한 유저의 수를 리턴하는 메서드
	 * @param boardId
	 * @return
	 */
	public long countByBoard(long boardId) {
		log.info("countByBoard(id = {})", boardId);
		
		return bookmarkRepository.countBookmarkByBoardId(boardId);
	}
	
	/**
	 * 사용자의 게시판 즐겨찾기 목록을 불러오는 메서드
	 * @param userId
	 * @return
	 */
	public Page<BookmarkListDto> findAllByUserId(long userId, Pageable pageable) {
		log.info("findAllByUserId(id = {})", userId);
		
		return bookmarkRepository.findAllByUserId(userId, pageable);
	}

}
