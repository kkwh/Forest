package com.example.forest.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.forest.dto.board.BoardCreateDto;
import com.example.forest.model.BoardCategory;
import com.example.forest.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("/create")
	@PreAuthorize("hasRole('USER')")
	public String create(Principal principal, Model model) {
		BoardCategory[] categories = BoardCategory.values();
		model.addAttribute("categories", categories);
		
		return "board/create";
	}
	
	@PostMapping("/create")
	public String create(BoardCreateDto dto) {
		log.info("create(dto = {})", dto);
		
		boardService.createBoard(dto);
		
		return "redirect:/";
	}

}
