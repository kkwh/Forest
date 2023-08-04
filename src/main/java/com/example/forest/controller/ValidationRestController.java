package com.example.forest.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.forest.dto.user.UserFindEmailDto;
import com.example.forest.dto.user.UserValidateDto;
import com.example.forest.service.EmailService;
import com.example.forest.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/validate")
public class ValidationRestController {
	
    private final UserService userService;
    private final EmailService emailService;
    
    //로그인 아이디 중복확인
    @PostMapping("/checkLoginId")
    @ResponseBody
    public ResponseEntity<Integer> validateLoginId(@RequestBody UserValidateDto dto) {
        log.info("validateLoginId(dto = {})", dto);
        //log.info("컨트롤러도 잘 왔어요");
        int result = userService.validateLoginId(dto.getLoginId());
        
        return ResponseEntity.ok(result);
    }
    
    //로그인 닉네임 중복확인
    @PostMapping("/checkLoginNickname")
    @ResponseBody
    public ResponseEntity<Integer> validateLoginNickname(@RequestBody UserValidateDto dto) {
        log.info("validateLoginNickname(dto = {}", dto);
        
        int result = userService.validateLoginNickname(dto.getNickname());
        return ResponseEntity.ok(result);
    }
    
    //로그인 이메일 중복확인 + 아이디 있나 없나 확인.
      @PostMapping("/checkLoginEmail")
      @ResponseBody
      public ResponseEntity<Integer> validateLoginEmail(@RequestBody UserValidateDto dto) {
          log.info("validateLoginEmail(dto = {}", dto);
          
          int result = userService.validateLoginEmail(dto.getEmail());
          return ResponseEntity.ok(result);
      }
     
      //이메일로 인증받기.
      @GetMapping("/emailConfirm")
      public ResponseEntity<String> emailConfirm(@RequestParam("email") String email) throws Exception {
          
        String confirm = emailService.sendSimpleMessage(email);
        
        log.info("confirm = {}", confirm);

        return ResponseEntity.ok(confirm);
      }
      
      //아이디 찾기 이메일 인증받기.
      @GetMapping("/findIdEmailConfirm")
      public String findIdEmailConfirm(@RequestParam String email) throws Exception {
          log.info("이메일 아이디 찾기 컨트롤러 왔어요");
          String confirm = emailService.sendSimpleMessage(email);
          
          return confirm;
      }
     
  
 
}
