package com.example.forest.service;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.example.forest.model.Likes;
import com.example.forest.model.Post;
import com.example.forest.model.User;
import com.example.forest.repository.LikesRepository;
import com.example.forest.repository.PostRepository;
import com.example.forest.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LikesService {
    @Autowired
    private LikesRepository likesRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
        
    
    /**
	 * 유저가 어떠한 게시글에서 좋아요 또는 싫어요를 눌렀을 때 정보를 저장하는 메서드
	 * @param likeDislike(1 -> 좋아요, 0 -> 싫어요)
	 * @param postId
	 * @param userId
	 */
    public void saveLikeDislikeForPost(int likeDislike, long postId, long userId) {
        Post post = postRepository.findById(postId).orElseThrow();
        log.info("post: {}", post);
        
        Likes likes = null;
        if(userId != 0) {
        User user = userRepository.findById(userId).orElseThrow();
        log.info("post: {}, user: {}", post, user);
        
            likes = Likes.builder()
                .likeDislike(likeDislike)
                .post(post)
                .user(user)
                .build();
        }
        else {
            likes = Likes.builder()
                    .likeDislike(likeDislike)
                    .post(post)
                    .build();
        }
        
        
        likesRepository.save(likes);
    }
    
    /**
	 * 게시글의 좋아요 수를 리턴하는 메서드
	 * @param postId
	 * @return
	 */
    public long countLikesByPostId(long postId) {        
        log.info("countLikesByPostId(postId={})", postId);
        
        return likesRepository.countLikesByPostId(postId);
    }
    
    /**
	 * 게시글의 싫어요 수를 리턴하는 메서드
	 * @param postId
	 * @return
	 */
    public long countDislikesByPostId(long postId) {
        log.info("countLikesByPostId(postId={})", postId);
        
        return likesRepository.countDislikesByPostId(postId);
    }
    
    /**
	 * 해당 id에 해당하는 게시글을 삭제하는 메서드
	 * @param postId
	 */
    public void deleteByPost_Id(long postId) {
        likesRepository.deleteByPost_Id(postId);
    }
    
    /**
	 * 사용자(유저)가 지난 24시간 동안 해당 게시물에
	 * 좋아요 또는 싫어요 액션을 취했는지 확인하는 메서드 
	 * @param userId
	 * @param postId
	 * @param likeDislike
	 * @return
	 */
    public boolean likeOrDislike(Long userId, Long postId, int likeDislike) {
        // 지난 24시간 동안 해당 유저가 해당 게시물에 대해 액션을 취했는지 확인
        if (likesRepository.existsByUserIdAndPostIdAndCreatedTimeGreaterThan(userId, postId, LocalDate.now().minusDays(1))) {
            return false; // 이미 액션을 취했다면 false 반환
        }
        
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        
        // 액션(좋아요, 싫어요) 저장
        Likes likes = Likes.builder()
                .user(user)
                .post(post)
                .likeDislike(likeDislike)
                .build();
        likesRepository.save(likes);
        return true;
    }
    
    /**
	 * userId와 postId를 받아서 Likes.id를 반환하는 메서드
	 * @param userId
	 * @param postId
	 * @return
	 */
    public Long findLikeIdByUserIdAndPostId(Long userId, Long postId) {
        try {
            return likesRepository.findIdByUserIdAndPostId(userId, postId);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
    
    /**
   	 * 사전에 Likes 테이블에 해당 데이터가 존재하는지 여부를 확인하는 메서드
   	 * @param postId
   	 * @param userId
   	 * @return
   	 */
    public boolean isLikeDislikeExists(Long postId, Long userId) {
        Long likeId = likesRepository.findIdByUserIdAndPostId(userId, postId);
        return likeId != null;
    }
    
}
