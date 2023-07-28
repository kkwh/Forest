package com.example.forest.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	/**
	 * Security Config 테스트 용으로 만듦
	 * 향후 삭제 예정
	 * @return
	 */
	@GetMapping("/test")
	//@PreAuthorize("hasRole('USER')")
	public String test() {
		return "test";
	}

}
