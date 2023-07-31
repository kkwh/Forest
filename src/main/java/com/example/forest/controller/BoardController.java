package com.example.forest.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.forest.dto.board.BoardCreateDto;
import com.example.forest.dto.board.BoardDetailDto;
import com.example.forest.dto.board.BoardListDto;
import com.example.forest.dto.board.BoardModifyDto;
import com.example.forest.model.BlackList;
import com.example.forest.model.BoardCategory;
import com.example.forest.model.User;
import com.example.forest.service.BoardService;
import com.example.forest.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
	
	private final UserService userService;
	private final BoardService boardService;
	
	@GetMapping("/mainLand")
	public String getMainBoard(Model model) {
		return "board/main";
	}
	
	@GetMapping("/subLand")
	public String getSubBoard(Model model) {
		Map<BoardCategory, List<BoardListDto>> boardMap = new HashMap<>();
		
		BoardCategory[] categories = BoardCategory.values();
		for(BoardCategory category : categories) {
			List<BoardListDto> subList = boardService.findAllByCategory(category, "Sub");
			
			boardMap.put(category, subList);
		}
		
		model.addAttribute("boardMap", boardMap);
		
		return "board/sub";
	}
	
	@GetMapping("/create")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public String create(Principal principal, Model model) {
		BoardCategory[] categories = BoardCategory.values();
		model.addAttribute("categories", categories);
		
		long userId = userService.getUserId(principal.getName());
		model.addAttribute("userId", userId);
		
		return "board/create";
	}
	
	@PostMapping("/create")
	public String create(BoardCreateDto dto) {
		log.info("create(dto = {})", dto);
		log.info("file = {}", dto.getImageFile().getOriginalFilename());
		
		boardService.createBoard(dto);
		
		return "redirect:/";
	}
	
	@GetMapping("/list")
	@PreAuthorize("hasRole('USER')")
	public String getBoardList(Principal principal, Model model) {
		log.info("getBoardList()");
		
		BoardCategory[] categories = BoardCategory.values();
		model.addAttribute("categories", categories);
		
		List<BoardListDto> boards = boardService.findAllByUser(principal.getName());
		model.addAttribute("boards", boards);
		
		long userId = userService.getUserId(principal.getName());
		log.info("userId = {}", userId);
		model.addAttribute("userId", userId);
		
		return "user/boardList";
	}
	
	@GetMapping("/details/{id}")
	@PreAuthorize("isAuthenticated()")
	public String details(@PathVariable long id, Principal principal, Model model) {
		log.info("detais(id = {})", id);
		
		BoardDetailDto dto = boardService.findById(id);
		model.addAttribute("board", dto);
		
		List<BlackList> blackList = boardService.getBlackList(id);
		model.addAttribute("blackList", blackList);
		
		long userId = userService.getUserId(principal.getName());
		
		List<User> users = boardService.getUserList(id, userId);
		model.addAttribute("userList", users);
		
		log.info("users = {}", users);
		
		return "board/details";
	}
	
	@PostMapping("/modify")
	public String modify(BoardModifyDto dto) {
		log.info("modify(dto = {})", dto);
		
		boardService.updateBoard(dto);
		
		return "redirect:/admin/board/list";
	}

}
