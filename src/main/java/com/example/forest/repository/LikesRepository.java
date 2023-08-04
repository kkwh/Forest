package com.example.forest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.model.Likes;
import com.example.forest.model.Post;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    @Modifying
    @Transactional
    @Query("INSERT INTO Likes (likeDislike, post) SELECT :likeDislike, p FROM Post p WHERE p.id = :postId")
    void saveLikeDislikeForPost(int likeDislike, long postId); // 좋아요: 1, 싫어요: 0 값을 삽입하는 메서드 
    
    // 해당 게시글의 총 좋아요 개수를 리턴하는 메서드
    @Query("SELECT COUNT(l) FROM Likes l WHERE l.post.id = :postId AND l.likeDislike = 1")
    long countLikesByPostId(@Param("postId") long postId);
    
    // 해당 게시글의 총 싫어요 개수를 리턴하는 메서드
    @Query("SELECT COUNT(l) FROM Likes l WHERE l.post.id = :postId AND l.likeDislike = 0")
    long countDislikesByPostId(@Param("postId") long postId);
    
    /**
     * 게시물 삭제시 좋아요 기록 삭제
     * @param post
     */
    @Transactional
	@Modifying
    void deleteByPost(@Param("post") Post post);
}
