package com.example.forest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.example.forest.dto.board.BoardListDto;
import com.example.forest.dto.board.BoardRankListDto;
import com.example.forest.model.BoardCategory;
import com.example.forest.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {
    

    private final BoardService boardService;
    
	@GetMapping("/")
	public String home(Model model) {
	        Map<BoardCategory, List<BoardListDto>> boardMap = new HashMap<>();
        
	        // 랭킹 목록(인기순위, 최근개설)
	       BoardRankListDto rankDtoSub = boardService.findPopularBoard("Sub", 4);
	       log.info("rankDtoSub = {}", rankDtoSub);
	       model.addAttribute("rankListSub", rankDtoSub);
	        
	        BoardRankListDto rankDtoMain = boardService.findPopularBoard("Main", 4);
	        log.info("rankDtoMain = {}", rankDtoMain);
	        model.addAttribute("rankListMain", rankDtoMain);

	    
		return "index";
	}
	
	@GetMapping("/accessDenied")
	public String accessDenied() {
		return "accessDenied";
	}
	
}
