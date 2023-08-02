package com.example.forest.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.forest.dto.user.UserSignUpDto;
import com.example.forest.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
    private final UserService userService;
    
    
    @GetMapping("/createuser")
    public void createuser() {
        log.info("createuser() GET");
    }
    
    @PostMapping("/createuser")
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
}
