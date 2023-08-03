package com.example.forest.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.forest.dto.board.BlackListDto;
import com.example.forest.dto.board.BoardCheckDto;
import com.example.forest.dto.board.BoardListDto;
import com.example.forest.dto.board.BoardRevokeDto;
import com.example.forest.dto.board.BoardSearchDto;
import com.example.forest.dto.board.BoardUserDto;
import com.example.forest.model.BlackList;
import com.example.forest.model.BoardCategory;
import com.example.forest.model.User;
import com.example.forest.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/board")
public class BoardApiController {
	
	private final BoardService boardService;
	
	/**
	 * 사용자가 게시판을 등록할 때 게시판 이름의 중복 여부를 체크
	 * @param dto
	 * @return
	 */
	@PostMapping("/checkName")
	@ResponseBody
	public ResponseEntity<Integer> checkName(@RequestBody BoardCheckDto dto) {	
		int result = boardService.checkBoardNameExists(dto.getBoardName());
		
		return ResponseEntity.ok(result);
	}
	
	/**
	 * 관리자가 게시판 생성을 승인
	 * @param boardId
	 * @return
	 */
	@PutMapping("/approve/{boardId}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> approve(@PathVariable long boardId) {
		log.info("approve(id = {})", boardId);
		
		boardService.approveBoard(boardId);
		
		return ResponseEntity.ok("Success");
	}
	
	/**
	 * 관리자가 게시판 생성을 거절
	 * @param boardId
	 * @return
	 */
	@PutMapping("/decline/{boardId}")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> decline(@PathVariable long boardId) {
		log.info("decline(id = {})", boardId);
		
		boardService.declineBoard(boardId);
		
		return ResponseEntity.ok("Success");
	}
	
	/**
	 * 관리자가 게시판 생성자의 권한을 뺏음
	 * @param dto
	 * @return
	 */
	@PutMapping("/revoke")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> revoke(@RequestBody BoardRevokeDto dto, Principal principal) {
		log.info("revoke(dto = {})", dto);
		
		boardService.revokeAuthority(dto, principal.getName());
		
		return ResponseEntity.ok("Success");
	}
	
	/**
	 * 게시판의 등급을 승급(강등) 시킴
	 * @param id
	 * @return
	 */
	@PutMapping("/updateGrade/{id}")
	public ResponseEntity<String> updateBoardGrade(@PathVariable long id) {
		log.info("updateBoardGrade(id = {})", id);
		
		boardService.updateBoardGrade(id);
		
		return ResponseEntity.ok("Success");
	}
	
	/**
	 * 게시판 관리자가 특정 유저의 접근을 차단 시킴
	 * @param dto
	 * @return
	 */
	@PutMapping("/blockById")
	@ResponseBody
	public ResponseEntity<String> blockByUserId(@RequestBody BoardUserDto dto) {
		log.info("blockUser(dto = {})", dto);
		
		boardService.addToList(dto);
		
		return ResponseEntity.ok("Success");
	}
	
	/**
	 * 게시판 관리자가 특정 IP의 접근을 차단 시킴
	 * @param dto
	 * @return
	 */
	@PutMapping("/blockByIp")
	@ResponseBody
	public ResponseEntity<String> blockByIP(@RequestBody BoardUserDto dto) {
		log.info("blockUser(dto = {})", dto);
		
		boardService.addToList(dto);
		
		return ResponseEntity.ok("Success");
	}
	
	/**
	 * 게시판 관리자가 특정 유저의 접근 제한을 해제함
	 * @param dto
	 * @return
	 */
	@PutMapping("/cancelBlock")
	@ResponseBody
	public ResponseEntity<String> cancelBlock(@RequestBody BoardUserDto dto) {
		log.info("cancelBlock(dto = {})", dto);
		
		boardService.removeFromList(dto);
		
		return ResponseEntity.ok("Success");
	}
	
	@PostMapping("/searchUnapproved")
	@ResponseBody
	public ResponseEntity<List<BoardListDto>> searchUnapproved(@RequestBody BoardSearchDto dto) {
		log.info("searchByKeyword({})", dto);
		
		List<BoardListDto> boards = boardService.findAllByApprovalStatus(dto, 0);
		
		return ResponseEntity.ok(boards);
	}
	
	@PostMapping("/searchApproved")
	@ResponseBody
	public ResponseEntity<List<BoardListDto>> searchApproved(@RequestBody BoardSearchDto dto) {
		log.info("searchByKeyword({})", dto);
		
		List<BoardListDto> boards = boardService.findAllByApprovalStatus(dto, 1);
		
		return ResponseEntity.ok(boards);
	}
	
//	
//	@PostMapping("/sortBy")
//	@ResponseBody
//	public ResponseEntity<List<BoardListDto>> sortBy(@RequestBody BoardSearchDto dto) {
//		log.info("sortBy({})", dto);
//		
//		List<BoardListDto> boards = boardService.findAllOrderByType(dto);
//		
//		return ResponseEntity.ok(boards);
//	}
	
	/**
	 * 정렬 조건에 따라 게시판을 검색
	 * @param dto
	 * @return
	 */
	@PostMapping("/filterBy")
	@ResponseBody
	public ResponseEntity<List<BoardListDto>> filterBy(@RequestBody BoardSearchDto dto) {
		log.info("filterBy(dto = {})", dto);	

		List<BoardListDto> list = boardService.findAllOrderByType(dto);
		
		return ResponseEntity.ok(list);
	}
	
	/**
	 * 카테고리 별로 게시판을 검색
	 * @param category
	 * @param boardGrade
	 * @return
	 */
	@GetMapping("/searchByCategory")
	public ResponseEntity<List<BoardListDto>> searchByCategory(@RequestParam("category") BoardCategory category, @RequestParam("boardGrade") String boardGrade) {
		log.info("searchByCategory(category = {})", category);
		
		List<BoardListDto> list = boardService.findAllByCategory(category, boardGrade);
		
		return ResponseEntity.ok(list);
	}
	
	/**
	 * 키워드를 사용하여 게시판을 검색
	 * @param keyword
	 * @param boardGrade
	 * @return
	 */
	@GetMapping("/searchByKeyword")
	@ResponseBody
	public ResponseEntity<List<BoardListDto>> searchByKeyword(@RequestParam("keyword") String keyword, @RequestParam("boardGrade") String boardGrade) {
		log.info("searchByKeyword(keyword = {})", keyword);
		
		List<BoardListDto> list = boardService.findAllByKeyword(keyword, boardGrade);
		
		return ResponseEntity.ok(list);
	}

	/**
	 * 해당 게시판에 등록되어 있는 블랙리스트를 불러옴
	 * @param boardId
	 * @return
	 */
	@PostMapping("/getBlackList/{boardId}")
	public ResponseEntity<List<BlackListDto>> getBlackList(@PathVariable("boardId") long boardId) {
		log.info("getBlackList(id = {})", boardId);
		
		List<BlackListDto> blackList = boardService.getBlackList(boardId);
		
		return ResponseEntity.ok(blackList);
	}
	
	/**
	 * 해당 게시판에서 블랙리스트에 등록되어 있지 않은 유저리스트를 불러옴
	 * @param dto
	 * @return
	 */
	@PostMapping("/getUserList")
	@ResponseBody
	public ResponseEntity<List<User>> getUserList(@RequestBody BoardUserDto dto) {
		log.info("getUserList()");
		
		List<User> users = boardService.getUserList(dto.getBoardId(), dto.getUserId());
		
		return ResponseEntity.ok(users);
	}
	
	@PostMapping("/checkAccess")
	@ResponseBody
	public ResponseEntity<Integer> checkUserAccss(@RequestBody BoardUserDto dto) {
		log.info("checkUserAccess()");
		
		int result = boardService.checkUserBoardAccess(dto);
		
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteBoard(@PathVariable("id") long id) {
		log.info("deleteBoard(id = {})", id);
		
		boardService.deleteBoard(id);
		
		return ResponseEntity.ok("Success");
	}

}
