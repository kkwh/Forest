package com.example.forest.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forest.dto.board.BookmarkCreateDto;
import com.example.forest.dto.board.BookmarkListDto;
import com.example.forest.service.BookmarkService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookmark")
public class BookmarkRestController {
	
	private final BookmarkService bookmarkService;
	
	/**
	 * 사용자가 해당 게시판을 즐겨찾기 등록했는지 확인함
	 * @param boardId
	 * @param userId
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Integer> checkClicked(@Param("boardId") long boardId, @RequestParam("userId") long userId) {
		log.info("checkClicked(boardId = {}, userId = {})", boardId, userId);
		
		int result = bookmarkService.isExisting(boardId, userId);
		log.info("result = {}", result);
		
		return ResponseEntity.ok(result);
	}
	
	/**
	 * 해당 게시판을 즐겨찾기 등록한 유저의 수를 가져옴
	 * @param boardId
	 * @return
	 */
	@GetMapping("/count/{boardId}")
	public ResponseEntity<Long> count(@PathVariable("boardId") long boardId) {
		log.info("count(id = {})", boardId);
		
		long count = bookmarkService.countByBoard(boardId);
		log.info("count = {}", count);
		
		return ResponseEntity.ok(count);
	}
	
	/**
	 * 유저의 즐겨찾기 목록을 찾음
	 * @param userId
	 * @return
	 */
//	@GetMapping("/getList")
//	public ResponseEntity<Page<BookmarkListDto>> getList(@RequestParam("userId") long userId, 
//					@PageableDefault(page = 0, size = 3) Pageable pageable) {
//		log.info("getList(userId = {})", userId);
//		
//		Page<BookmarkListDto> list = bookmarkService.findAllByUserId(userId, pageable);
//		int currPage = list.getPageable().getPageNumber() + 1;
//    	int startPage = Math.max(currPage - 4, 1);
//    	int endPage = Math.min(currPage + 5, list.getTotalPages());
//    	
//    	model.addAttribute("currPage", currPage);
//    	model.addAttribute("startPage", startPage);
//    	model.addAttribute("endPage", endPage);
//		
//		return ResponseEntity.ok(list);
//	}
	
	/**
	 * 유저가 해당 게시판을 즐겨찾기 목록에 추가
	 * @param dto
	 * @return
	 */
	@PostMapping("/addToBookmark")
	public ResponseEntity<String> addToBookmark(@RequestBody BookmarkCreateDto dto) {
		log.info("addToBookmark(dto = {})", dto);
		
		bookmarkService.add(dto);
		
		return ResponseEntity.ok("Success");
	}
	
	/**
	 * 유저의 즐겨찾기에서 해당 게시판을 제거
	 * @param boardId
	 * @param userId
	 * @return
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@RequestParam("boardId") long boardId, @RequestParam("userId") long userId) {
		log.info("delete(boardId = {}, userId = {})", boardId, userId);
		
		bookmarkService.delete(boardId, userId);
		
		return ResponseEntity.ok("Success");
	}
	
	/**
	 * 유저의 즐겨찾기에서 해당 게시판을 제거
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") long id) {
		log.info("delete(id = {})", id);
		
		bookmarkService.delete(id);
		
		return ResponseEntity.ok("Success");
	}

}
