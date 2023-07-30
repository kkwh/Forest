package com.example.forest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forest.dto.reply.ReplyCreateDto;
import com.example.forest.model.Reply;
import com.example.forest.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyRestController {

        private final ReplyService replyService;
            
        // pathVariable이라고 부르는 부분은 중괄호로 표시
        @GetMapping("/all/{postId}")
        public ResponseEntity<List<Reply>> all(@PathVariable long postId) {
            log.info("all(postId={})", postId);
            
            List<Reply> list = replyService.read(postId);
            
            // 클라이언트로 댓글 리스트를 응답으로 보냄.
            return ResponseEntity.ok(list);
        }
        
        @PostMapping
         public ResponseEntity<Reply> create(@RequestBody ReplyCreateDto dto) {
             log.info("create(dto={})", dto);
            
             Reply reply = replyService.create(dto);
             log.info("reply={}", reply);
            
             return ResponseEntity.ok(reply);
            
         }
        
        @DeleteMapping("/{id}")
        public ResponseEntity<String> delete(@PathVariable long id) {
            log.info("delete(id={})", id);
            
            replyService.delete(id);
            
            return ResponseEntity.ok("Success");
            
        }        
    
}
