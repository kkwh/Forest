package com.example.forest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.forest.dto.stats.PostUserDto;
import com.example.forest.dto.stats.UserJoinDto;
import com.example.forest.repository.PostRepository;
import com.example.forest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminUserService {
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	public List<UserJoinDto> getUserSignupStats() {
		log.info("getUserSignupStats()");
		
		return userRepository.getUserJoinStats();
	}
	
	public List<PostUserDto> jsonWriterCounts() {
		log.info("getPostWriterStats()");
		
		return postRepository.getPostWriterStats();
	}

}
