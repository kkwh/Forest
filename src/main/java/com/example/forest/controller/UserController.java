package com.example.forest.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.forest.dto.user.UserInfoUpdateDto;
import com.example.forest.dto.user.UserReplyDto;
import com.example.forest.dto.user.UserSignUpDto;
import com.example.forest.model.Board;
import com.example.forest.model.BoardCategory;
import com.example.forest.model.Post;
import com.example.forest.model.Reply;
import com.example.forest.model.User;
import com.example.forest.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
    private final UserService userService;
    
    @GetMapping("/signup")
    public String createuser() {
        log.info("createuser() GET");
        
        return "/user/createuser";
    }
    
    @PostMapping("/signup")
    public String createuser(UserSignUpDto dto) {
        log.info("createuser(dto={})",dto);
        
       Long id = userService.registerUser(dto);
       
       log.info("회원 가입 id", id);
       
       return "/user/login";
    }
    
	
    @GetMapping("/login")
    public String showLoginPage(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("errorMessage", "아이디 혹은 비밀번호를 확인해주세요"); // 에러 메시지를 모델에 추가
        }
        return "user/login";
    }
    
    @GetMapping("/finduserinfo")
    public void findpw(){
        log.info("아이디,비밀번호 찾기 페이지");
    }
    
    
    
   
    @PreAuthorize("hasRole('USER')") 
    @GetMapping("/myinfopage")
    public void myinfopage() {
        log.info("내정보 보기 페이지 도착.");
    }
   
    
    @PreAuthorize("hasRole('USER')") 
    @GetMapping("/updateinfo")
    public void updatenickname(Model model, Principal principal){
        long userId = 0;
        if(principal != null) {
            userId = userService.getUserId(principal.getName());
        }
        log.info("userId: {}", userId);
        model.addAttribute("userId", userId);
        
        log.info("닉네임바꾸기 페이지");
    }
    
    @PreAuthorize("hasRole('USER')") 
    @ResponseBody
    @PostMapping("/updateinfo")
    public ResponseEntity<String> updateinfo(@RequestBody UserInfoUpdateDto dto ) {
        log.info("update(dto={})", dto);
       
           userService.update(dto);
        
        return ResponseEntity.ok("성공");
    }
 
 
    //갤로그 관련.
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/gallogmain")
    public String gallogMain(String loginId, Model model,Principal principal) {
     //   GallogBoardListDto boardListDto = userService.findAllByUserId();
     //   model.addAttribute("gallogDto", boardListDto); // "gallogDto"라는 이름으로 모델에 추가
        log.info("갤로그 페이지 컨트롤러");
        
        BoardCategory[] categories = BoardCategory.values();
        model.addAttribute("categories", categories);
        
        long userId= userService.getUserId(principal.getName());
        log.info("userId={}",userId);
        model.addAttribute("userId",userId);
        
        //보드 리스트
        List<Board> boards = userService.findAllByUser(principal.getName());
        model.addAttribute("boards",boards);
       
        
        long uId = 0; //uId = DB에서 유저 시퀀스 아이디
        if(principal != null) {
            uId = userService.getUserId(principal.getName());
        }
        log.info("uId: {}", uId);

        
        if(uId != 0) {
            User user = userService.findUserById(uId);
            model.addAttribute("user", user);
        }
        
        // 포스트 리스트
        List<Post> posts = userService.findAllByUserPost(uId);
        model.addAttribute("posts",posts);
      
        // 댓글 리스트
        List<UserReplyDto> replies = userService.findAllByUserReply(uId);
        model.addAttribute("replies", replies);
        
        return "user/gallogmain"; // 해당 뷰 이름 반환
        //패스는 자스에서 넘겨주는 값이 있어야함.
    }
    
    
}
