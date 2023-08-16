package com.example.forest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.forest.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 댓글에 달린 모든 대댓글 삭제
     * @param reply
     */
    @Transactional
    @Modifying
    void deleteByReply(@Param("reply") Reply reply);

    // 대댓글 ID와 비밀번호로 댓글 조회 메서드 추가
    @Query("select r "
            + " from ReReply r "
            + " where r.id = :id "
            + " and r.replyPassword2 = :replyPassword")
    ReReply findByIdAndReReplyPassword(@Param("id") Long id, @Param("replyPassword") String replyPassword);

    
    
}
