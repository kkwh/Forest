package com.example.forest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
    
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/accessDenied")
	public String accessDenied() {
		return "accessDenied";
	}
	
	@GetMapping("/donation/donation")
    public String donation(Model model) {
        log.info("donation");
        
        
       return "/donation/donation";
        
    }
	
}
