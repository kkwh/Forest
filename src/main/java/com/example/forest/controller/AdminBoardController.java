package com.example.forest.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.forest.dto.board.BoardListDto;
import com.example.forest.model.BoardCategory;
import com.example.forest.service.BoardService;
import com.example.forest.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/board")
public class AdminBoardController {
	
	private final UserService userService;
	private final BoardService boardService;
	
	/**
	 * 관리자의 승인이 필요한 게시판의 리스트를 불러옴
	 * @param model
	 * @return
	 */
	@GetMapping("/unapproved")
	@PreAuthorize("hasRole('ADMIN')")
	public String getUnapprovedList(Model model) {
		log.info("getUnapprovedList()");
		
		BoardCategory[] categories = BoardCategory.values();
		model.addAttribute("categories", categories);
		
		List<BoardListDto> boards = boardService.findAllByApprovalStatus(0);
		model.addAttribute("boards", boards);
		
		return "admin/unapprovedList";
	}
	
	/**
	 * 현재 승인된 게시판의 리스트를 불러옴
	 * @param model
	 * @return
	 */
	@GetMapping("/approved")
	@PreAuthorize("hasRole('ADMIN')")
	public String getApprovedList(Model model) {
		log.info("getApprovedList()");
		
		BoardCategory[] categories = BoardCategory.values();
		model.addAttribute("categories", categories);
		
		List<BoardListDto> boards = boardService.findAllByApprovalStatus(1);
		model.addAttribute("boards", boards);
		
		return "admin/approvedList";
	}
	
	@GetMapping("/list")
	@PreAuthorize("hasRole('ADMIN')")
	public String getBoardList(Principal principal, Model model) {
		log.info("getBoardList()");
		
		BoardCategory[] categories = BoardCategory.values();
		model.addAttribute("categories", categories);
		
		List<BoardListDto> boards = boardService.findAllByUser(principal.getName());
		model.addAttribute("boards", boards);
		
		long userId = userService.getUserId(principal.getName());
		log.info("userId = {}", userId);
		model.addAttribute("userId", userId);
		
		return "admin/boardList";
	}
	
}
