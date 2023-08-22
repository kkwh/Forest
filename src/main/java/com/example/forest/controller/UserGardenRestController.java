package com.example.forest.controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forest.dto.gallog.DiaryCreateDto;
import com.example.forest.model.Board;
import com.example.forest.model.Diary;
import com.example.forest.model.Post;
import com.example.forest.model.Reply;
import com.example.forest.service.GardenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/garden/gardenmain")
public class UserGardenRestController {
    
    private final GardenService gardenService;

    @PostMapping
    public ResponseEntity<Diary> create(@RequestBody DiaryCreateDto dto){
        log.info("create(dto={})",dto);
        
        Diary diary = gardenService.create(dto);
        log.info("diary={}", diary);
        
        return ResponseEntity.ok(diary);
    }
    
    @GetMapping("/getPostList/{userId}")
    public ResponseEntity<List<Post>> getPostList(@PathVariable("userId") long userId) {
        List<Post> postlist = gardenService.findByuserIdPosts(userId);
        log.info("postlist={}", postlist);
        
        return ResponseEntity.ok(postlist);
    }
    
    @GetMapping("/getBoardList/{userId}")
    public ResponseEntity<List<Board>> getBoardList(@PathVariable("userId") long userId) {
        List<Board> boardlist = gardenService.findByuserIdBoards(userId);
        log.info("boaldist={}",boardlist);
        
        return ResponseEntity.ok(boardlist);
    }
    
    @GetMapping("/getReplyList/{userId}")
    public ResponseEntity<List<Reply>> getReplyList(@PathVariable("userId") long userId) {
        List<Reply> replylist = gardenService.findByuserIdReplys(userId);
        log.info("replylist={}", replylist);
        
        return ResponseEntity.ok(replylist);
    }
    
}
