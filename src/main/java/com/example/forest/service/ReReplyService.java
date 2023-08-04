package com.example.forest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.reply.ReplyCreateDto;
import com.example.forest.dto.reply.reReplyCreateDto;
import com.example.forest.model.Post;
import com.example.forest.model.ReReply;
import com.example.forest.model.Reply;
import com.example.forest.repository.ReReplyRepository;
import com.example.forest.repository.ReplyRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class ReReplyService {
    
    private final ReReplyRepository reReplyRepository;
    private final ReplyRepository replyRepository;

    @Transactional(readOnly = true)
    public List<ReReply> read(Long replyId) {
        log.info("read(reply={})", replyId);
    
        // 1. Reply Id로 Reply 검색
        Reply reply = replyRepository.findById(replyId).orElseThrow();
        
        // 2. 찾은 Reply에 달려있는 대댓글 목록을 검색
        List<ReReply> list = reReplyRepository.findByReplyOrderByIdDesc(reply);
        
        return list;
    }
    
    // 대댓글 개수
    public Long countByReply(Reply reply) {
        log.info("countByReply(reply={})", reply);
        
        return reReplyRepository.countByReply(reply);
    }
    
    public Long countByPost(Post post) {
        log.info("countByPost(post = {})", post);
        
        return reReplyRepository.countReReplyByPost(post);
    }
    
    // 대댓글 생성
    public ReReply create(reReplyCreateDto dto) {
        log.info("rEcreate(dto={})", dto);

        
        // 1. Reply 엔터티검색
        Reply reply = replyRepository.findById(dto.getReplyId()).orElseThrow();
       
        // 2. ReplyCreateDto 객체를 Reply 엔터티 객체로 변환.
        ReReply entity = ReReply.builder()
                .reply(reply)
                .userId(dto.getUserId())
                .replyIp2(dto.getReReplyIp())
                .replyText2(dto.getReReplyText())
                .replyNickname2(dto.getReReplyNickname())
                .replyPassword2(dto.getReReplyPassword())
                .build();
                
        // 3. DB replies 테이블에 insert
        reReplyRepository.save(entity);
        
        return entity;
    }
    
}
