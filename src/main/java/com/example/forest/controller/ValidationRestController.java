package com.example.forest.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/checkLoginId")
    @ResponseBody
    public ResponseEntity<Integer> validateLoginId(@RequestBody UserValidateDto dto) {
        log.info("validateLoginId(dto = {})", dto);
        
        int result = userService.validateLoginId(dto.getLoginId());
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/checkLoginNickname")
    @ResponseBody
    public ResponseEntity<Integer> validateLoginNickname(@RequestBody UserValidateDto dto) {
        log.info("validateLoginNickname(dto = {}", dto);
        
        int result = userService.validateLoginNickname(dto.getNickname());
        return ResponseEntity.ok(result);
    }
    
      @PostMapping("/emailCheck")
      @ResponseBody
      public ResponseEntity<Integer> validateLoginEmail(@RequestBody UserValidateDto dto) {
          log.info("validateLoginEmail(dto = {}", dto);
          
          int result = userService.validateLoginEmail(dto.getEmail());
          return ResponseEntity.ok(result);
      }
     
      @PostMapping("/emailConfirm")
      public String emailConfirm(@RequestParam String email) throws Exception {

        String confirm = emailService.sendSimpleMessage(email);

        return confirm;
      }
   
 
}
