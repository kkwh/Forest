package com.example.forest.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forest.dto.reply.ReplyCreateDto;
import com.example.forest.dto.reply.ReplyListDto;
import com.example.forest.model.Reply;
import com.example.forest.service.IpService;
import com.example.forest.service.ReplyService;
import com.example.forest.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyRestController {

        private final ReplyService replyService;
        private final IpService ipService;
        private final UserService userService;
            
        // pathVariable이라고 부르는 부분은 중괄호로 표시
        @GetMapping("/all/{postId}")
        public ResponseEntity<ReplyListDto> all(@PathVariable long postId, Principal principal) {
            log.info("all(postId={})", postId);
            
            long userId = 0;
            if(principal != null) {
                userId = userService.getUserId(principal.getName());
            }
 
            List<Reply> list = replyService.read(postId);
            for (Reply reply : list) {
                String replyIp = ipService.getServerIp();
                log.info("포스트 ID: {}, IP 주소: {}", postId, replyIp);
            }
            
            ReplyListDto dto = ReplyListDto.builder()
                    .userId(userId)
                    .list(list)
                    .build();
            
            // 클라이언트로 댓글 리스트를 응답으로 보냄.
            return ResponseEntity.ok(dto);
        }
        
        @PostMapping
         public ResponseEntity<Reply> create(@RequestBody ReplyCreateDto dto, HttpServletRequest request, Principal principal) {
             log.info("create(dto={})", dto);

             // HttpServletRequest 객체에서 클라이언트 IP 주소 가져오기
             
             long userId = 0;
             if(principal != null) {
                 userId = userService.getUserId(principal.getName());
             }
             
            
             dto.setUserId(userId);
             
             String ipAddr = ipService.getServerIp();
             request.setAttribute("ipAddr", ipAddr);
             log.info("ip = {}", ipAddr);
            
          // 댓글 생성에 클라이언트 IP 주소 전달
             dto.setReplyIp(ipAddr);
             
             Reply reply = replyService.create(dto);
             
             
             log.info("reply={}", reply);
            
             return ResponseEntity.ok(reply);
            
         }
        
        // 회원 댓글 삭제
        @DeleteMapping("/{id}")
        public ResponseEntity<String> delete(@PathVariable long id, @RequestParam("password") String password, Principal principal) {
            log.info("delete(id={})", id);
            
            if(principal != null) {
                replyService.delete(id);
            } else {
                replyService.delete(id, password);
            }
            
            return ResponseEntity.ok("댓글 삭제 성공");
        
            
        }
        

    
}
