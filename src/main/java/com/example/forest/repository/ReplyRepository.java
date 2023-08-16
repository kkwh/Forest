package com.example.forest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.user.UserReplyDto;
import com.example.forest.model.Post;
import com.example.forest.model.Reply;
import com.example.forest.model.User;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // Post Id로 검색하기:
    List<Reply> findByPostId(Long postId);
        
    // Post로 검색하기:
    List<Reply> findByPostOrderByIdDesc(Post post);
    List<Reply> findByPostOrderByIdAsc(Post post); 

    
    // Post에 달린 댓글 개수:
    Long countByPost(Post post);
    
    // Post에 달린 댓글 개수: by postId
    Long countByPostId(Long postId);
    
    // 댓글 ID와 비밀번호로 댓글 조회 메서드 추가
    Reply findByIdAndReplyPassword(Long id, String replyPassword);
    
    /**
     * 서원준
     * 게시물에 작성된 모든 댓글 삭제
     * @param post
     */
    @Transactional
	@Modifying
    void deleteByPost(@Param("post") Post post);

    /**
    SELECT r.id AS id,reply_text, p.id AS id, board_id
    FROM replies r 
    JOIN posts p ON r.post_id = p.id
    where r.user_id = 23;
    **/
    /**
    @Transactional
    @Query(" select r.id As id, reply.text, p.id As id, board.id from Reply r Join Post p ON r.post.id = p.id "
            + " where r.user.id = :userId ")
    List<Reply> findAllByUserIdOrderByIdDesc(@Param("userId") long userId);
    **/
    
    @Query("select new com.example.forest.dto.user.UserReplyDto(r.id as id, r.replyText, p.id as postId, b.id as boardId, r.createdTime) "
            + " from Reply r, Post p, Board b "
            + " where r.post = p "
            + " and p.board = b "
            + " and r.userId = :userId "
            + " order by r.id desc") 
    List<UserReplyDto> findAllRepliesByUserId(@Param("userId") long userId);
   
 

}