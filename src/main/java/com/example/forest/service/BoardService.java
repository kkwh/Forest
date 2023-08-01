package com.example.forest.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.board.BoardCreateDto;
import com.example.forest.dto.board.BoardListDto;
import com.example.forest.dto.board.BoardRevokeDto;
import com.example.forest.dto.board.BoardSearchDto;
import com.example.forest.model.Board;
import com.example.forest.model.BoardCategory;
import com.example.forest.model.ImageFile;
import com.example.forest.model.User;
import com.example.forest.repository.BoardRepository;
import com.example.forest.repository.ImageFileRepository;
import com.example.forest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final FileService fileService;
	private final ImageFileRepository fileRepository;
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
		
		boardRepository.save(entity);
		log.info("entity = {}", entity);
		
		fileService.saveBoardProfileImage(dto.getImageFile(), entity.getId());
	}
	
	/**
	 * DB에서 불러온 게시판 리스트를 DTO 타입의 리스트로 변환하고 
	 * ImageFile을 추가하는 메서드
	 * @param boards
	 * @return
	 */
	private List<BoardListDto> addImageFile(List<Board> boards) {
		List<BoardListDto> list = new ArrayList<>();
		
		for(Board b : boards) {
			ImageFile file = fileRepository.findByBoardId(b.getId());
			
			BoardListDto dto = BoardListDto.fromEntity(b);
			dto.setFile(file);
			
			log.info("file = {}", file);
			
			list.add(dto);
		}
		
		return list;
	}
	
	/**
	 * 특정 카테고리에 있는 모든 랜드를 불러오는 메서드
	 * @param category
	 * @param grade
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<BoardListDto> findAllByCategory(BoardCategory category, String grade) {
		log.info("findAllByCategory({})", category);
		
		List<Board> boards = boardRepository.findAllByCategory(category, grade);
		
		return addImageFile(boards);
	}
	
	/**
	 * 해당 키워드가 포함되어 있는 모든 랜드를 불러오는 메서드
	 * @param keyword
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<BoardListDto> findAllByKeyword(BoardSearchDto dto) {
		log.info("findAllByKeyword({})", dto);
		
		User entity = userRepository.findById(dto.getUserId()).orElseThrow();
		
		List<Board> boards = boardRepository.findAllByKeyword(dto.getKeyword(), entity);
		
		return addImageFile(boards);
	}
	
	/**
	 * 게시물 목록을 정렬 조건에 따라 정렬하여 리턴하는 메서드
	 * @param dto
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<BoardListDto> findAllOrderByType(BoardSearchDto dto) {
		log.info("findAllOrderByType(dto = {})", dto);
		
		List<Board> boards = new ArrayList<>();
		User entity = userRepository.findById(dto.getUserId()).orElseThrow();
		
		switch(dto.getType()) {
		case "recent":
			boards = boardRepository.findAllByOrderByCreatedTimeDesc(entity);
			break;
		case "past":
			boards = boardRepository.findAllByOrderByCreatedTime(entity);
			break;
		case "name_asc":
			boards = boardRepository.findAllByOrderByBoardName(entity);
			break;
		case "name_desc":
			boards = boardRepository.findAllByOrderByBoardNameDesc(entity);
			break;
		}
		
		return addImageFile(boards);
	}
	
	/**
	 * 관리자의 승인이 필요한 게시판 목록을 불러오는 메서드
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<BoardListDto> findAllByApprovalStatus(int status) {
		log.info("findAllByApprovalStatus()");
		
		List<Board> boards = boardRepository.findAllBoardsByStatus(status);
		
		return addImageFile(boards);
	}
	
	/**
	 * 특정 사용자가 관리자 권한을 가지고 있는 게시판 목록을 불러오는 메서드
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<BoardListDto> findAllByUser(String username) {
		log.info("findAllByUserId(id = {})", username);
		
		User entity = userRepository.findByLoginId(username);
		List<Board> boards = boardRepository.findAllBoardsByUser(entity);
		
		return addImageFile(boards);
	}

	
	/**
	 * 게시판이 메인 랜드로 승급될 경우 게시판 등급을 변경하는 메서드
	 * @param boardId
	 */
	public void updateBoardGrade(long boardId) {
		log.info("update(id = {})", boardId);
		
		boardRepository.updateBoardGrade(boardId, "Main");
	}
	
	/**
	 * 관리자가 게시판 생성을 승인한 경우 승인 상태를 변경하는 메서드
	 * @param boardId
	 */
	public void approveBoard(long boardId) {
		log.info("approve(id = {})", boardId);
		
		boardRepository.approveBoard(boardId, 1);
	}

	/**
	 * 관리자가 게시판 생성을 거절할 경우 DB에 저장된 Board를 삭제하는 메서드
	 * @param boardId
	 */
	public void declineBoard(long boardId) {
		log.info("delete(id = {})", boardId);
		
		ImageFile file = fileRepository.findByBoardId(boardId);
		fileService.deleteImage(file);
		log.info("deleteImage()");
		
		Board entity = boardRepository.findById(boardId).orElseThrow();
		log.info("entity = {}", entity);
		
		boardRepository.delete(entity);
	}

	/**
	 * 관리자가 게시판 생성자의 관리자 권한을 회수하는 메서드
	 * @param dto
	 * @param loginId
	 */
	public void revokeAuthority(BoardRevokeDto dto, String loginId) {
		log.info("revokeAuthority(dto = {})", dto);
		
		User entity = userRepository.findByLoginId(loginId);
		log.info("entity = {}", entity);
		
		boardRepository.updateBoardOwner(entity, dto.getBoardId());
	}
	
}
