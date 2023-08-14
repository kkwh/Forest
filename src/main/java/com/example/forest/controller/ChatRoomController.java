package com.example.forest.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.forest.dto.chat.ChatRoomDto;
import com.example.forest.model.ChatRoom;
import com.example.forest.model.User;
import com.example.forest.service.ChatService;
import com.example.forest.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {
	
	private final UserService userService;
	private final ChatService chatService;
	
	@GetMapping("/list")
	public String getList(Model model) {
		log.info("getList()");
		
		List<ChatRoom> list = chatService.findAllRooms();
		model.addAttribute("rooms", list);
		
		return "chat/list";
	}
	
	@GetMapping("/room/create")
	@PreAuthorize("isAuthenticated()")
	public String createRoom() {
		log.info("createRoom()");
		
		return "chat/create";
	}
	
	@PostMapping("/room/create")
	public String create(@RequestParam("name") String name, Principal principal) {
		log.info("create(name = {})", name);
		
		String loginId = "";
		if(principal != null) {
			loginId = principal.getName();
		}
		
		chatService.create(name, loginId);
		
		return "redirect:/chat/list";
	}
	
	@GetMapping("/room/{id}")
	@PreAuthorize("isAuthenticated()")
	public String room(@PathVariable("id") long id, Principal principal, Model model) {
		log.info("room(id = {})", id);
		
		User user = userService.findUserByLoginId(principal.getName());
		model.addAttribute("user", user);
		
		ChatRoom room = chatService.getRoom(id);
		model.addAttribute("room", room);
		
		return "chat/room";
	}

}
