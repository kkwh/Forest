package com.example.forest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.forest.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/board")
public class BoardApiController {
	
	private final BoardService boardService;
	
	@PostMapping("/checkName")
	@ResponseBody
	public ResponseEntity<Integer> checkName(@RequestParam("boardName") String boardName) {
		log.info("checkName({})", boardName);
		
		int result = boardService.checkBoardNameExists(boardName);
		log.info("result = {}", result);
		
		return ResponseEntity.ok(result);
	}

}
