package com.example.forest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forest.dto.chat.ChatMessageDto;
import com.example.forest.model.ChatMessage;
import com.example.forest.model.ChatRoom;
import com.example.forest.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat")
public class ChatRoomApiController {
	
	private final ChatService chatService;
	
	@GetMapping("/messages/{roomId}")
	public ResponseEntity<List<ChatMessageDto>> getMessages(@PathVariable("roomId") long roomId) {
		log.info("getMessages(id = {})", roomId);
		
		List<ChatMessageDto> list = chatService.getMessages(roomId);
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/getList")
	public ResponseEntity<List<ChatRoom>> getRoomsByKeyword(@RequestParam String keyword) {
		log.info("getRoomsByKeyword(keyword = {})", keyword);
		
		List<ChatRoom> list = chatService.findAllRoomsByKeyword(keyword);
		
		return ResponseEntity.ok(list);
	}

}
