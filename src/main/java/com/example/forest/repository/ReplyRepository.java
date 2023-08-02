package com.example.forest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forest.model.Post;
import com.example.forest.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // Post Id로 검색하기:
    List<Reply> findByPostId(Long postId);
        
    // Post로 검색하기:
    List<Reply> findByPostOrderByIdDesc(Post post);
    
    // Post에 달린 댓글 개수:
    Long countByPost(Post post);
    
    // Post에 달린 댓글 개수: by postId
    Long countByPostId(Long postId);
    
    // 댓글 ID와 비밀번호로 댓글 조회 메서드 추가
    Optional<Reply> findByIdAndReplyPassword(Long id, String replyPassword);
    
}