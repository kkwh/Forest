package com.example.forest.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.forest.dto.stats.BoardCountDto;
import com.example.forest.dto.stats.PostUserDto;
import com.example.forest.dto.stats.UserJoinDto;
import com.example.forest.service.AdminBoardService;
import com.example.forest.service.AdminUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	private final AdminBoardService boardService;
	private final AdminUserService userService;

	@GetMapping("/main")
	@PreAuthorize("hasRole('ADMIN')")
	public String main(Model model) throws Exception {
		log.info("main() GET");
		
		// 카테고리별 생성된 랜드 수
		List<BoardCountDto> boardCounts = boardService.countBoardByCategory();
		log.info("boardCount = {}", boardCounts);

		String jsonBoardCounts = objectMapper.writeValueAsString(boardCounts);
		model.addAttribute("jsonBoardCounts", jsonBoardCounts);
		
		// 날짜별 신규 가입자 수
		List<UserJoinDto> joinCounts = userService.getUserSignupStats();
		log.info("boardCount = {}", boardCounts);
		
		String jsonJoinCounts = objectMapper.writeValueAsString(joinCounts);
		model.addAttribute("jsonJoinCounts", jsonJoinCounts);
		
		// 날짜별 신규 가입자 수
		List<PostUserDto> writerCounts = userService.jsonWriterCounts();
		log.info("boardCount = {}", boardCounts);
		
		String jsonWriterCounts = objectMapper.writeValueAsString(writerCounts);
		model.addAttribute("jsonWriterCounts", jsonWriterCounts);
		
		return "admin/main";
	}

}
