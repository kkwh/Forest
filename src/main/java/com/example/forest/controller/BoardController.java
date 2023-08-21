package com.example.forest.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.forest.dto.board.BlackListDto;
import com.example.forest.dto.board.BoardCreateDto;
import com.example.forest.dto.board.BoardDetailDto;
import com.example.forest.dto.board.BoardListDto;
import com.example.forest.dto.board.BoardModifyDto;
import com.example.forest.dto.board.BoardRankListDto;
import com.example.forest.dto.post.PostWithLikesCount;
import com.example.forest.model.BoardCategory;
import com.example.forest.model.User;
import com.example.forest.service.BoardService;
import com.example.forest.service.PostService;
import com.example.forest.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
	
	private final PostService postService;
	private final UserService userService;
	private final BoardService boardService;
	
	@GetMapping("/mainLand")
	public String getMainBoard(Model model, HttpServletRequest request) {
		Map<BoardCategory, List<BoardListDto>> boardMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");
		log.info("loginId = {}", loginId);
		
		long userId = 0;
		if(loginId != null) {
			userId = userService.getUserId(loginId);
		}
		
		model.addAttribute("userId", userId);
		
		BoardCategory[] categories = BoardCategory.values();
		for(BoardCategory category : categories) {
			List<BoardListDto> subList = boardService.findAllByCategory(category, "Main");
			
			boardMap.put(category, subList);
		}
		
		model.addAttribute("boardMap", boardMap);
		
		BoardRankListDto rankDto = boardService.findPopularBoard("Main", 5);
		log.info("rankDto = {}", rankDto);
		model.addAttribute("rankList", rankDto);
		
		return "board/main";
	}
	
	@GetMapping("/subLand")
	public String getSubBoard(Model model, HttpServletRequest request) {
		Map<BoardCategory, List<BoardListDto>> boardMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");
		log.info("loginId = {}", loginId);
		
		long userId = 0;
		if(loginId != null) {
			userId = userService.getUserId(loginId);
		}
		
		model.addAttribute("userId", userId);
		
		BoardCategory[] categories = BoardCategory.values();
		for(BoardCategory category : categories) {
			List<BoardListDto> subList = boardService.findAllByCategory(category, "Sub");
			
			boardMap.put(category, subList);
		}
		
		model.addAttribute("boardMap", boardMap);
		
		BoardRankListDto rankDto = boardService.findPopularBoard("Sub", 5);
		log.info("rankDto = {}", rankDto);
		model.addAttribute("rankList", rankDto);
		
		return "board/sub";
	}
	
	@GetMapping("/{id}") // 해당 랜드의 home
	public String board(@PathVariable("id") long id, Model model, Principal principal, @PageableDefault(page = 0, size = 10) Pageable pageable, HttpServletRequest request) {
		BoardDetailDto dto = boardService.findById(id);
		model.addAttribute("board", dto);
		
		Page<PostWithLikesCount> list = postService.findAllPostsWithLikesCount(id, pageable);
        
		int nowPage = 0;
		int startPage = 0;
		int endPage = 0;
		
		if (list.getTotalPages() == 0) {
		    nowPage = 0; // 페이지가 비어 있으면 현재 페이지를 0으로 설정
		    startPage = 0;
		    endPage = 0;
		} else {
		    nowPage = list.getPageable().getPageNumber() + 1;
		    startPage = Math.max(nowPage - 4, 1);
		    endPage = Math.min(nowPage + 5, list.getTotalPages());
		}
		
	    log.info("post(list={})", list);
	
	    model.addAttribute("posts", list);
	    model.addAttribute("nowPage", nowPage);
	    model.addAttribute("startPage", startPage);
	    model.addAttribute("endPage", endPage);
	    
	    long userId = 0;
        if(principal != null) {
            userId = userService.getUserId(principal.getName());
        }
        log.info("userId: {}", userId);
        model.addAttribute("userId", userId);
        
        if(userId != 0) {
            User user = userService.findUserById(userId);
            model.addAttribute("user", user);
            log.info("user: {}", user);
        }
        
        // 최근 방문 랜드
        LinkedList<Long> recentLands = (LinkedList<Long>) request.getSession().getAttribute("recentLands");
        if (recentLands == null) {
            recentLands = new LinkedList<>();
        }
        
        recentLands.remove((Long) id);
        recentLands.addFirst(id);
        
        while (recentLands.size() > 10) {
            recentLands.removeLast();
        }

        request.getSession().setAttribute("recentLands", recentLands);
        
        List<BoardDetailDto> recentLandBoards = recentLands.stream().map(boardService::findById).collect(Collectors.toList());
        model.addAttribute("recentLands", recentLandBoards);
        
        // 인기 순위 표시
        String grade = "Sub"; // 기본값으로 서브랜드 설정
        log.info("boardGrade: {}", dto.getBoardGrade());
        if ("Main".equalsIgnoreCase(dto.getBoardGrade())) {
            grade = "Main"; // 사용자가 메인랜드를 선택한 경우
        }
        
        long rank = postService.findRankByLandId(dto.getId(), grade);
        log.info("rank: {}", rank);
        model.addAttribute("rank", rank);

		return "board/read";
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
		log.info("boards = {}", boards);
		
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
		
		List<BlackListDto> blackList = boardService.getBlackList(id);
		model.addAttribute("blackList", blackList);
		
		long userId = 0;
		if(principal != null) {
			userId = userService.getUserId(principal.getName());
		}
		
		List<User> users = boardService.getUserList(id, userId);
		model.addAttribute("userList", users);
		
		log.info("users = {}", users);
		
		return "board/details";
	}
	
	@PostMapping("/modify")
	public String modify(BoardModifyDto dto) {
		log.info("modify(dto = {})", dto);
		
		boardService.updateBoard(dto);
		
		return "redirect:/board/list";
	}

}
