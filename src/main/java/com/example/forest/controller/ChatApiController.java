package com.example.forest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forest.dto.chat.ChatMessageCreateDto;
import com.example.forest.dto.chat.ChatMessageDto;
import com.example.forest.dto.chat.ChatRoomDto;
import com.example.forest.model.ChatMessage;
import com.example.forest.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat")
public class ChatApiController {
	
	private final ChatService chatService;
	
	/**
	 * 특정 채팅방의 메시지들을 넘겨줌
	 * @param roomId
	 * @return
	 */
	@GetMapping("/messages/{roomId}")
	public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable("roomId") long roomId) {
		log.info("getMessages(id = {})", roomId);
		
		List<ChatMessage> list = chatService.getMessages(roomId);
		
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/save")
	public ResponseEntity<String> saveMessage(@RequestBody ChatMessageCreateDto dto) {
		log.info("saveMessage(dto = {})", dto);
		
		chatService.create(dto);
		
		return ResponseEntity.ok("Success");
	}
	
	/**
	 * 검색된 채팅방 목록을 전달함
	 * @param keyword
	 * @return
	 */
	@GetMapping("/getList")
	public ResponseEntity<List<ChatRoomDto>> getRoomsByKeyword(@RequestParam String keyword) {
		log.info("getRoomsByKeyword(keyword = {})", keyword);
		
		List<ChatRoomDto> list = chatService.findAllRoomsByKeyword(keyword);
		
		return ResponseEntity.ok(list);
	}
	
	@DeleteMapping("/deleteMessage/{id}")
	public ResponseEntity<String> deleteMessage(@PathVariable("id") long id) {
		log.info("deleteMessage(id = {})", id);
		
		chatService.delete(id);
		
		return ResponseEntity.ok("Success");
	}

}
