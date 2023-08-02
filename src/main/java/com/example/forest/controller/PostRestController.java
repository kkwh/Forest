package com.example.forest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forest.dto.post.ModifyPasswordCheckDto;
import com.example.forest.dto.post.PostLikesDto;
import com.example.forest.model.Post;
import com.example.forest.service.LikesService;
import com.example.forest.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostRestController {
    
	private final PostService postService;
	private final LikesService likesService;
	
	@PostMapping("/check-password")
    public boolean checkPassword(@RequestBody ModifyPasswordCheckDto dto) {
		log.info("checkPassword(dto={})", dto);
		
        String inputPassword = dto.getPassword();
        Long postId = dto.getPostId();

        Post post = postService.read(postId);
        log.info("post={}", post);

        return inputPassword.equals(post.getPostPassword());
    }
	
	@Transactional
	@DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable long postId) {
        log.info("deletePost(id={})", postId);
        
        likesService.deleteByPost_Id(postId);
        
        postService.delete(postId);
        
        return ResponseEntity.ok("Likes for postId " + postId + " has been deleted.");
    }
	
	// 조회수
	@PostMapping("/increaseViewCount")
    public int increaseViewCount(@RequestParam Long postId) {
    	log.info("increaseViewCount(postId={})", postId);
    	
        return postService.increaseViewCount(postId);
    }
	
	// 총 좋아요 개수
	@PostMapping("/like")
    public long createLike(@RequestBody PostLikesDto dto) {
    	log.info("createLike(postId={})", dto.getPostId());
    	
        likesService.saveLikeDislikeForPost(1, dto.getPostId());
        
        return likesService.countLikesByPostId(dto.getPostId());
    }
	
	// 총 싫어요 개수
	@PostMapping("/dislike")
    public long createDislike(@RequestBody PostLikesDto dto) {
    	log.info("createDislike(postId={})", dto.getPostId());
    	
    	likesService.saveLikeDislikeForPost(0, dto.getPostId());
        
        return likesService.countDislikesByPostId(dto.getPostId());
    }
	
    
}
