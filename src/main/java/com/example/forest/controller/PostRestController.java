package com.example.forest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forest.dto.post.ModifyPasswordCheckDto;
import com.example.forest.model.Post;
import com.example.forest.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostRestController {
    
	private final PostService postService;
	
	@PostMapping("/check-password")
    public boolean checkPassword(@RequestBody ModifyPasswordCheckDto dto) {
		log.info("checkPassword(dto={})", dto);
		
        String inputPassword = dto.getPassword();
        Long postId = dto.getPostId();

        Post post = postService.read(postId);
        log.info("post={}", post);

        return inputPassword.equals(post.getPostPassword());
    }
	
	@DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable long postId) {
        log.info("deletePost(id={})", postId);
        
        postService.delete(postId);
        
        return ResponseEntity.ok("success");
    }
	
	@PostMapping("/increaseViewCount")
    public int increaseViewCount(@RequestParam Long postId) {
    	log.info("increaseViewCount(postId={})", postId);
    	
        return postService.increaseViewCount(postId);
    }
	
	@PostMapping("/like")
    public int createLike(@RequestParam Long postId) {
    	log.info("createLike(postId={})", postId);
    	
        return postService.increaseViewCount(postId);
    }
	
	@PostMapping("/dislike")
    public int createDislike(@RequestParam Long postId) {
    	log.info("createDislike(postId={})", postId);
    	
        return postService.increaseViewCount(postId);
    }
	
    
}
