package com.example.forest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.board.BoardCreateDto;
import com.example.forest.dto.board.BoardListDto;
import com.example.forest.model.Board;
import com.example.forest.model.BoardCategory;
import com.example.forest.model.User;
import com.example.forest.repository.BoardRepository;
import com.example.forest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final UserRepository userRepository;
	private final BoardRepository boardRepository;
	
	/**
	 * 사용자가 게시판을 생성할 때 해당 이름을 가진 게시판이 존재하는지 확인하는 메서드
	 * @param boardName
	 * @return 0 -> 존재하지 않음, 1 -> 존재함
	 */
	public int checkBoardNameExists(String boardName) {
		Board entity = boardRepository.findByBoardName(boardName);
		
		if(entity == null) {
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * 새로운 게시판(랜드)를 생성하는 메서드
	 * @param dto
	 */
	public void createBoard(BoardCreateDto dto) {
		log.info("create(dto = {})", dto);
		
		// 게시판을 생성하려는 User 객체를 찾음
		User creator = userRepository.findById(dto.getUserId()).orElseThrow();
		
		// DB에 저장할 Board 객체 생성
		Board entity = dto.toEntity();
		entity.setUser(creator);
		
		log.info("entity = {}", entity);
		
		boardRepository.save(entity);
	}
	
	/**
	 * 특정 카테고리에 있는 모든 랜드를 불러오는 메서드
	 * @param category
	 * @param grade
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<BoardListDto> findAllByCategory(BoardCategory category, String grade) {
		log.info("getAllByCategory({})", category);
		
		List<Board> boards = boardRepository.findAllByCategory(category, grade);
		
		return boards.stream()
				.map(board -> BoardListDto.fromEntity(board))
				.toList();
	}
	
	/**
	 * 해당 키워드가 포함되어 있는 모든 랜드를 불러옴
	 * @param keyword
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<BoardListDto> findAllByKeyword(String keyword) {
		log.info("findAllByKeyword({})", keyword);
		
		List<Board> boards = boardRepository.findAllByKeyword(keyword);
		
		return boards.stream()
				.map(board -> BoardListDto.fromEntity(board))
				.toList();
	}

}
