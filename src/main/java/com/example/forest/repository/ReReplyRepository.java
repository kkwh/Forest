package com.example.forest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.forest.model.Post;
import com.example.forest.model.ReReply;
import com.example.forest.model.Reply;

public interface ReReplyRepository extends JpaRepository<ReReply, Long> {
    
    // Reply Id로 검색하기:
    List<ReReply> findByReplyId(Long ReplyId);
    
    // Reply로 검색하기:
    List<ReReply> findByReplyOrderByIdDesc(Reply reply);
    
    // Reply에 달린 댓글 개수:
    Long countByReply(Reply reply);
    
    @Query("select count(rr.id)"
            + " from Reply r, Post p, ReReply rr "
            + " where r.post = p "
            + " and rr.reply = r "
            + " and p = :post")
    Long countReReplyByPost(@Param("post") Post post);
}
