package com.example.forest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.forest.dto.user.UserSignUpDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
//    private final UserService userService;
    
    
    @GetMapping("/signup")
    public void signUp() {
        log.info("signUp() GET");
    }
    
    @PostMapping("/signup")
    public String signUp(UserSignUpDto dto) {
        log.info("signUp(dto={})",dto);
        
//       Long id = userService.registerUser(dto);
//       log.info("회원 가입 id", id);
       
       return "index";
    }
	
}
