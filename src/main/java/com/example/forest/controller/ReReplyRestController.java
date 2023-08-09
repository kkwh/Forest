package com.example.forest.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forest.dto.reply.ReplyCreateDto;
import com.example.forest.dto.reply.reReplyCreateDto;
import com.example.forest.model.ReReply;
import com.example.forest.model.Reply;
import com.example.forest.service.IpService;
import com.example.forest.service.ReReplyService;
import com.example.forest.service.ReplyService;
import com.example.forest.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/reReply")
@RequiredArgsConstructor
public class ReReplyRestController {

    private final ReplyService replyService;
    private final ReReplyService reReplyService;
    private final IpService ipService;
    private final UserService userService;
    
    // pathVariable이라고 부르는 부분은 중괄호로 표시
    @GetMapping("/all/{replyId}")
    public ResponseEntity<List<ReReply>> all(@PathVariable Long replyId) {
        log.info("all(replyId={})", replyId);
        
        List<ReReply> list = reReplyService.read(replyId);
       
        // 클라이언트로 댓글 리스트를 응답으로 보냄.
        return ResponseEntity.ok(list);
    }
    
    // 대댓글 생성(IP 포함)
    @PostMapping
     public ResponseEntity<String> create(@RequestBody reReplyCreateDto dto, HttpServletRequest request, Principal principal) {
         log.info("recreate(dto={})", dto);

         // HttpServletRequest 객체에서 클라이언트 IP 주소 가져오기
         
         long userId = 0;
         if(principal != null) {
             userId = userService.getUserId(principal.getName());
         }
         
        
         dto.setUserId(userId);

         String ipAddrfor = ipService.getServerIp();
         String ipAddr = ipAddrfor.substring(0, ipAddrfor.lastIndexOf(".", ipAddrfor.lastIndexOf(".")-1));
         request.setAttribute("ipAddr", ipAddr);
         log.info("ip = {}", ipAddr);
        
      // 댓글 생성에 클라이언트 IP 주소 전달
         dto.setReReplyIp(ipAddr);
         
         ReReply reReply = reReplyService.create(dto);
         
         
         log.info("reReply={}", reReply);
        
         return ResponseEntity.ok("성공");
        
     }
    
    
    // 대댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete2(@PathVariable long id, @RequestParam("password") String password, Principal principal) {
        log.info("delete(id={})", id);
        
        if(principal != null) {
            reReplyService.delete2(id);
        } else {
            reReplyService.delete2(id, password);
        }
        
        return ResponseEntity.ok("대댓글 삭제 성공");
    }
    
}