package com.example.forest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.forest.model.Post;
import com.example.forest.model.Reply;
import com.example.forest.repository.ReplyRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class ReplyService {

    private final ReplyRepository replyRepository;
    
    public List<Reply> read(Post post) {
        log.info("read(post={})", post);
    
        List<Reply> list = replyRepository.findByPostOrderByIdDesc(post);
        
        return list;
    }
    
    public Long countByPost(Post post) {
        log.info("countByPost(post={})", post);
        
        return replyRepository.countByPost(post);
    }
    
    
}
