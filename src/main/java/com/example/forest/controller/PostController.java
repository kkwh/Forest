package com.example.forest.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.forest.dto.board.BoardDetailDto;
import com.example.forest.dto.post.PostCreateDto;
import com.example.forest.dto.post.PostSearchDto;
import com.example.forest.dto.post.PostUpdateDto;
import com.example.forest.dto.post.PostWithLikesCount;
import com.example.forest.dto.post.PostWithLikesCount2;
import com.example.forest.model.Post;
import com.example.forest.service.BoardService;
import com.example.forest.service.IpService;
import com.example.forest.service.LikesService;
import com.example.forest.service.PostService;
import com.example.forest.service.ReplyService;
import com.example.forest.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {
    
	private final BoardService boardService;
    private final PostService postService;
    private final LikesService likesService;
    private final ReplyService replyService; 
    private final UserService userService; 
    private final IpService ipService;
    
    @GetMapping("/popular")
    public String popular(@RequestParam("id") long id, Model model) { // id - boardId
        log.info("popular(id={})", id);
        BoardDetailDto dto = boardService.findById(id);
        model.addAttribute("board", dto);
        
        List<PostWithLikesCount2> list = postService.findPostsByLikesDifference(id);
        log.info("popular(list={})", list);
        
        model.addAttribute("posts", list);
        
        return "/post/read-popular";
    }
    
//    @PreAuthorize("hasRole('USER')") // 페이지 접근 이전에 인증(권한, 로그인) 여부를 확인.
    @GetMapping("/create")
    public void create(@RequestParam("id") long id, Principal principal, Model model) { // id - boardId
        log.info("create() GET");
        
        long boardId = boardService.findById(id).getId();
        model.addAttribute("boardId", boardId);
        
        BoardDetailDto dto = boardService.findById(id);
        model.addAttribute("board", dto);
        
        long userId = 0;
        if(principal != null) {
            userId = userService.getUserId(principal.getName());
        }
        log.info("userId: {}", userId);
        
        String ip = ipService.getServerIp();
        String shortIp = ip.substring(0, ip.lastIndexOf(".", ip.lastIndexOf(".") - 1));
        log.info("IP 주소: {}", ip);
        log.info("Short IP 주소: {}", shortIp);
        
        model.addAttribute("shortIp", shortIp);
    }
    
    @PostMapping("/create")
    public String createPost(PostCreateDto dto) {
        log.info("create(dto={}) POST", dto);
        
       postService.create(dto);
        
        // DB 테이블 insert 후 포스트 목록 페이지로 redirect 이동.
        return "redirect:/board/" + dto.getBoardId();
    }
    
    
    // 채한별  추가:  댓글 개수 불러오기
    @GetMapping("/details")
    public void details(@RequestParam("boardId") long boardId, @RequestParam("id") long id, Model model) { 
        log.info("details(boardId={})", boardId);
        log.info("details(id={})", id);
        model.addAttribute("boardId", boardId);
        
        BoardDetailDto dto = boardService.findById(boardId);
        model.addAttribute("board", dto);
        
        Post post = postService.read(id);
        long likesCount = likesService.countLikesByPostId(id);
        long dislikesCount = likesService.countDislikesByPostId(id);
        long viewCount = postService.increaseViewCount(id) - 1;
//        long replyCount = replyService.countByPostId(id);
               
        model.addAttribute("post", post);
        model.addAttribute("likesCount", likesCount);
        model.addAttribute("dislikesCount", dislikesCount);
        model.addAttribute("viewCount", viewCount);
//        model.addAttribute("replyCount", replyCount);
        
        // 채한별 추가 : 
        // REPLIES 테이브에서 해당 포스트에 달린 댓글 개수를 검색.
        long count = replyService.countByPost(post);
        model.addAttribute("replyCount", count);
    }
    
    @GetMapping("/modifyCheck")
    public void modifyCheck(Long id, Model model) {
        log.info("modifyCheck(id={})", id);
        
        Post post = postService.read(id);
        
        model.addAttribute("post", post);
        
        BoardDetailDto dto = boardService.findById(postService.findBoardIdByPostId(id));
        model.addAttribute("board", dto);
    }
    
    @GetMapping("/modify")
    public void modify(Long id, Model model) {
        log.info("modify(id={})", id);
        
        Long boardId = postService.findBoardIdByPostId(id);
        log.info("boardId={}", boardId);
        model.addAttribute("boardId", boardId);
        
        BoardDetailDto dto = boardService.findById(boardId);
        model.addAttribute("board", dto);
        
        Post post = postService.read(id);
               
        model.addAttribute("post", post);
    }
    
    @PostMapping("/modify")
    public String modify(PostUpdateDto dto) {
        log.info("modify(dto={}) POST", dto);
        
       postService.update(dto);
        
       return "redirect:/board/" + postService.findBoardIdByPostId(dto.getId());
    }
    
    @GetMapping("/deleteCheck")
    public void deleteCheck(Long id, Model model) {
        log.info("deleteCheck(id={})", id);
        
        Post post = postService.read(id);
        
        model.addAttribute("post", post);
               
        BoardDetailDto dto = boardService.findById(postService.findBoardIdByPostId(id));
        model.addAttribute("board", dto);
    }
    
    @GetMapping("/search")
    public String search(PostSearchDto dto, Model model) {
        log.info("search(dto={})", dto);
        
        // postService의 검색 기능 호출:
        List<PostWithLikesCount> list = postService.search(dto);
        
        // 검색 결과를 Model에 저장해서 뷰로 전달:
        model.addAttribute("posts", list);
        log.info("search(list={})", list);
        
        BoardDetailDto dto2 = boardService.findById(dto.getBoardId());
        model.addAttribute("board", dto2);
        
        return "/board/read";
    }
    

}

