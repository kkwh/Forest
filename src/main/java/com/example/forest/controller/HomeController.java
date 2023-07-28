package com.example.forest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.forest.service.UserService;

@Controller
public class HomeController {
	
    
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	

	
}
