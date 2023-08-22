package com.example.forest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.forest.dto.stats.BoardCountDto;
import com.example.forest.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminBoardService {
	
	private final BoardRepository boardRepository;
	
	public List<BoardCountDto> countBoardByCategory() {
		log.info("countBoardByCategory()");
		
		return boardRepository.countBoardByCategory();
	}

}
