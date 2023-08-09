package com.example.forest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.board.BlackListDto;
import com.example.forest.dto.board.BoardCreateDto;
import com.example.forest.dto.board.BoardDetailDto;
import com.example.forest.dto.board.BoardListDto;
import com.example.forest.dto.board.BoardModifyDto;
import com.example.forest.dto.board.BoardRankDto;
import com.example.forest.dto.board.BoardRankListDto;
import com.example.forest.dto.board.BoardRevokeDto;
import com.example.forest.dto.board.BoardSearchDto;
import com.example.forest.dto.board.BoardUserDto;
import com.example.forest.model.BlackList;
import com.example.forest.model.Board;
import com.example.forest.model.BoardCategory;
import com.example.forest.model.ImageFile;
import com.example.forest.model.Post;
import com.example.forest.model.Reply;
import com.example.forest.model.Role;
import com.example.forest.model.User;
import com.example.forest.repository.BlackListRepository;
import com.example.forest.repository.BoardRepository;
import com.example.forest.repository.ImageFileRepository;
import com.example.forest.repository.LikesRepository;
import com.example.forest.repository.PostRepository;
import com.example.forest.repository.ReReplyRepository;
import com.example.forest.repository.ReplyRepository;
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
	private final PostRepository postRepository;
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	private final ReReplyRepository reReplyRepository;
	private final LikesRepository likesRepository;
	private final BlackListRepository blackListRepository;
	
	/**
	 * 사용자가 게시판을 생성할 때 해당 이름을 가진 게시판이 존재하는지 확인하는 메서드
	 * @param boardName
	 * @return 0 -> 존재하지 않음, 1 -> 존재함
	 */
	public int checkBoardNameExists(String boardName) {
		Board entity = boardRepository.findByBoardName(boardName);
		
		return (entity == null) ? 0 : 1;
	}
	
	public int checkUserBoardAccess(BoardUserDto dto) {
		BlackList entity = blackListRepository.findByBoardIdAndUserId(dto.getBoardId(), dto.getUserId());
		
		return (entity == null) ? 0 : 1;
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
	 * DB에서 불러온 게시판 리스트를 DTO 타입으로 변환하고 
	 * ImageFile을 추가하는 메서드
	 * @param board
	 * @return
	 */
	private BoardDetailDto addImageFile(Board board) {
		ImageFile file = fileRepository.findByBoardId(board.getId());
		
		BoardDetailDto dto = BoardDetailDto.fromEntity(board);
		dto.setFile(file);
		
		return dto;
	}
	
	/**
	 * 게시판의 상세 정보를 불러오는 메서드
	 * @param boardId
	 * @return
	 */
	@Transactional(readOnly = true)
	public BoardDetailDto findById(long boardId) {
		log.info("findById(id = {})", boardId);
		
		Board entity = boardRepository.findById(boardId).orElseThrow();
		
		long totalPosts = boardRepository.countPostsByBoardId(boardId);
		
		BoardDetailDto dto = addImageFile(entity);
		dto.setTotalPosts(totalPosts);
		
		return dto;
	}
	
	/**
	 * 특정 카테고리에 있는 모든 랜드를 랜드 등급에 따라 불러오는 메서드
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
	 * 특정 카테고리에 있는 모든 랜드를 불러오는 메서드
	 * @param category
	 * @return
	 */
//	@Transactional(readOnly = true)
//	public List<BoardListDto> findAllByCategory(BoardSearchDto dto) {
//		log.info("findAllByCategory(dto = {})", dto);
//		
//		User entity = userRepository.findById(dto.getUserId()).orElseThrow();
//		List<Board> boards = boardRepository.findAllByCategory(entity);
//		
//		return addImageFile(boards);
//	}
	
	/**
	 * 해당 키워드가 포함되어 있는 모든 랜드를 불러오는 메서드
	 * @param keyword
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<BoardListDto> findAllByKeyword(String keyword, String boardGrade) {
		log.info("findAllByKeyword(keyword = {})", keyword);
		
		List<Board> boards = boardRepository.findAllByKeyword(keyword, boardGrade);
		
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
			boards = boardRepository.findAllByOrderByCreatedTimeDesc(entity, dto.getKeyword(), dto.getCategoryList());
			break;
		case "past":
			boards = boardRepository.findAllByOrderByCreatedTime(entity, dto.getKeyword(), dto.getCategoryList());
			break;
		case "name_asc":
			boards = boardRepository.findAllByOrderByBoardName(entity, dto.getKeyword(), dto.getCategoryList());
			break;
		case "name_desc":
			boards = boardRepository.findAllByOrderByBoardNameDesc(entity, dto.getKeyword(), dto.getCategoryList());
			break;
		}
		
		return addImageFile(boards);
	}
	
	/**
	 * 관리자의 승인이 필요한 게시판 목록을 불러오는 메서드
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<BoardListDto> findAllByApprovalStatus(BoardSearchDto dto, int status) {
		log.info("findAllByApprovalStatus()");
		
		List<Board> boards = new ArrayList<>();
		
		switch(dto.getType()) {
		case "recent":
			boards = boardRepository.findAllByOrderByCreatedTimeDesc(dto.getKeyword(), dto.getCategoryList(), status);
			break;
		case "past":
			boards = boardRepository.findAllByOrderByCreatedTime(dto.getKeyword(), dto.getCategoryList(), status);
			break;
		case "name_asc":
			boards = boardRepository.findAllByOrderByBoardName(dto.getKeyword(), dto.getCategoryList(), status);
			break;
		case "name_desc":
			boards = boardRepository.findAllByOrderByBoardNameDesc(dto.getKeyword(), dto.getCategoryList(), status);
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
		List<Board> boards = boardRepository.findAllBoardsByUserOrderByIdDesc(entity);
		
		return addImageFile(boards);
	}

	
	/**
	 * 게시판 등급을 변경하는 메서드
	 * @param boardId
	 */
	public void updateBoardGrade(long boardId) {
		log.info("update(id = {})", boardId);
		
		Board entity = boardRepository.findById(boardId).orElseThrow();
		switch(entity.getBoardGrade()) {
		case "Sub":
			boardRepository.updateBoardGrade(boardId, "Main");
			break;
		case "Main":
			boardRepository.updateBoardGrade(boardId, "Sub");
			break;
		}
	}
	
	/**
	 * 게시판의 정보가 변경될 경우 적용할 메서드
	 * @param dto
	 * @throws IOException 
	 */
	public void updateBoard(BoardModifyDto dto) {
		log.info("boardUpdate(dto = {})", dto);
		
		boardRepository.updateBoardInfo(dto.getBoardInfo(), dto.getBoardId());
		
		// 랜드 배경 사진도 변경한 경우
		if(dto.getImageFile().getSize() != 0) {
			ImageFile entity = fileRepository.findByBoardId(dto.getBoardId());
			
			fileService.deleteImage(entity);
			
			fileService.saveBoardProfileImage(dto.getImageFile(), dto.getBoardId());
		}
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
	
	/**
	 * 해당 게시판에서 블랙 리스트에 등록되어 있는 유저 리스트를 불러오는 메서드
	 * @param boardId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<BlackListDto> getBlackList(long boardId) {
		log.info("getBlackList(id = {})", boardId);
		
		return blackListRepository.findAllByBoardId(boardId);
	}
	
	/**
	 * 특정 게시판에서 전체 유저중에 블랙리스트에 등록되어 있지 않은 유저만 불러오는 메서드
	 * @param boardId
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<User> getUserList(long boardId, long userId) {
		log.info("getUserList(boardId = {}, userId = {})", boardId, userId);;
		
		return blackListRepository.findAllUserNotInList(boardId, userId, Role.ADMIN);
	}
	
	public void addToList(BoardUserDto dto) {		
		BlackList entity = null;
		
		if(dto.getIpAddr() != null) {
			entity = BlackList.builder()
					.boardId(dto.getBoardId())
					.ipAddress(dto.getIpAddr())
					.build();
		} 
		else {
			entity = BlackList.builder()
					.boardId(dto.getBoardId())
					.userId(dto.getUserId())
					.build();
		}
		
		blackListRepository.save(entity);
	}
	
	public void removeFromList(BoardUserDto dto) {
		BlackList entity = blackListRepository.findByBoardIdAndUserId(dto.getBoardId(), dto.getUserId());
		
		log.info("entity = {}", entity);
		
		blackListRepository.delete(entity);
	}
	
	/**
	 * 게시판을 삭제하는 메서드
	 * @param boardId
	 */
	public void deleteBoard(long boardId) {
		log.info("deleteBoard(id = {})", boardId);
		
		Board entity = boardRepository.findById(boardId).orElseThrow();
		
		List<Post> posts = boardRepository.findAllPostsByBoard(entity);
		
		List<Reply> replies = new ArrayList<>();
		for(Post p : posts) {
			List<Reply> reps = boardRepository.findAllRepliesByPost(p);
			for(Reply r : reps) {
				replies.add(r);
			}
		}
		
		// 게시물에 작성된 대댓글 삭제
		for(Reply reply : replies) {
			reReplyRepository.deleteByReply(reply);
		}
		
		// 게시물에 작성된 댓글 삭제
		for(Post post : posts) {
			replyRepository.deleteByPost(post);
		}
		
		// 좋아요 기록 삭제
		for(Post post : posts) {
			likesRepository.deleteByPost(post);
		}
		
		// 게시판에 작성된 게시물 삭제
		postRepository.deleteByBoard(entity);
		
		// 게시판의 프로필 사진 삭제
		ImageFile image = fileRepository.findByBoardId(boardId);
		fileService.deleteImage(image);
		
		// 블랙리스트 내역 삭제
		blackListRepository.deleteByBoardId(entity.getId());
		
		// 게시판 삭제
		boardRepository.delete(entity);
	}
	
	/**
	 * 인기 랜드를 리스트로 불러오는 메서드
	 * @param boardGrade
	 * @return
	 */
	public BoardRankListDto findPopularBoard(String boardGrade) {
		log.info("findPopularBoard(grade = {})", boardGrade);
		
		List<BoardRankDto> list = boardRepository.findTop10Boards(boardGrade);
		List<BoardRankDto> rank1 = new ArrayList<>();
		List<BoardRankDto> rank2 = new ArrayList<>();
		int idx = 0;
		while(idx < 5 && idx < list.size()) {
			rank1.add(list.get(idx));
			idx++;
		}
		
		while(idx >= 5 && idx < list.size()) {
			rank2.add(list.get(idx));
			idx++;
		}
		
		List<Board> boards = boardRepository.findAllByBoardGradeOrderByCreatedTimeDesc(boardGrade);
		List<BoardListDto> date1 = new ArrayList<>();
		List<BoardListDto> date2 = new ArrayList<>();
		
		idx = 0;
		while(idx < 5 && idx < boards.size()) {
			date1.add(BoardListDto.fromEntity(boards.get(idx)));
			idx++;
		}
		
		while(idx >= 5 && idx < boards.size() && idx < 10) {
			date2.add(BoardListDto.fromEntity(boards.get(idx)));
			idx++;
		}
		
		return BoardRankListDto.builder()
				.top5List(rank1)
				.top10List(rank2)
				.list1(date1)
				.list2(date2)
				.build();
	}
	
}
