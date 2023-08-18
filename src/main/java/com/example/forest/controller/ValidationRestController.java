package com.example.forest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.forest.dto.user.UserFindEmailDto;
import com.example.forest.dto.user.UserFindPasswordDto;
import com.example.forest.dto.user.UserInfoUpdateDto;
import com.example.forest.dto.user.UserSignUpDto;
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

    // 로그인 아이디 중복확인
    @PostMapping("/checkLoginId")
    @ResponseBody
    public ResponseEntity<Integer> validateLoginId(@RequestBody UserValidateDto dto) {
        log.info("validateLoginId(dto = {})", dto);
        // log.info("컨트롤러도 잘 왔어요");
        int result = userService.validateLoginId(dto.getLoginId());

        return ResponseEntity.ok(result);
    }

    // 로그인 닉네임 중복확인
    @PostMapping("/checkLoginNickname")
    @ResponseBody
    public ResponseEntity<Integer> validateLoginNickname(@RequestBody UserValidateDto dto) {
        log.info("validateLoginNickname(dto = {}", dto);

        int result = userService.validateLoginNickname(dto.getNickname());
        return ResponseEntity.ok(result);
    }

    // 로그인 이메일 중복확인 + 아이디 있나 없나 확인.
    @PostMapping("/checkLoginEmail")
    @ResponseBody
    public ResponseEntity<Integer> validateLoginEmail(@RequestBody UserValidateDto dto) {
        log.info("validateLoginEmail(dto = {}", dto);

        int result = userService.validateLoginEmail(dto.getEmail());
        return ResponseEntity.ok(result);
    }

    // 회원가입 시 이메일로 인증받기.
    @GetMapping("/emailConfirm")
    public ResponseEntity<String> emailConfirm(@RequestParam("email") String email) throws Exception {

        String confirm = emailService.sendSimpleMessage(email);

        log.info("confirm = {}", confirm);

        return ResponseEntity.ok(confirm);
    }

    // 이메일로 아이디 찾기 (DB)
    @PostMapping("/findId")
    @ResponseBody
    public ResponseEntity<String> findId(@RequestParam("email") String email, Object userId) {
        log.info("UserFindEmailDto(email = {})", email);

        String loginId = userService.findLoginIdByEmail(email);
        log.info("loginId = {}", loginId);

        return ResponseEntity.ok(loginId);

    }
    
   
   
    //비밀번호 찾을 때 , 유저 DB에 아이디와 이메일이 둘 다 동일한 사람이 있는지 확인. 
    @PostMapping("/findPw")
    @ResponseBody
    public ResponseEntity<Integer> findPw(@RequestParam("email") String email, @RequestParam("loginId") String loginId) {
        log.info("validateLoginEmail(dto = {}", email, loginId);

        int result = userService.findPw(email, loginId);
        return ResponseEntity.ok(result);
    }
    
    //임시 비밀번호로 바꾸기 (수정하기 기능)
    //갤로그 회원가입 동시에 DB만들기
    
    
    

}
