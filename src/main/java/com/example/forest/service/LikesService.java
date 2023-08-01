package com.example.forest.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.forest.model.Likes;
import com.example.forest.model.Post;
import com.example.forest.repository.LikesRepository;
import com.example.forest.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LikesService {
    @Autowired
    private LikesRepository likesRepository;
    
    @Autowired
    private PostRepository postRepository;

    public void saveLikeDislikeForPost(int likeDislike, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        Likes likes = Likes.builder()
                .likeDislike(likeDislike)
                .post(post)
                .build();
        likesRepository.save(likes);
    }
    
    public long countLikesByPostId(long postId) {        
        log.info("countLikesByPostId(postId={})", postId);
        
        return likesRepository.countLikesByPostId(postId);
    }
    
    public long countDislikesByPostId(long postId) {
        log.info("countLikesByPostId(postId={})", postId);
        
        return likesRepository.countDislikesByPostId(postId);
    }
    
    
}
