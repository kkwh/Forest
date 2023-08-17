package com.example.forest.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.forest.dto.chat.ChatMessageCreateDto;
import com.example.forest.dto.chat.ChatMessageDto;
import com.example.forest.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@CrossOrigin(origins = "http://localhost:8090")
public class StompChatController {
	
	private final ChatService chatService;
	
	private final SimpMessagingTemplate template;
	
	@MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageCreateDto message){
		log.info("enter()");
		
		List<ChatMessageDto> list = chatService.getMessages(message.getRoomId());
		
        message.setMessage(message.getLoginId() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), list);
    }

    @MessageMapping("/chat/message/{roomId}")
    @SendTo("/sub/chat/room/{roomId}")
    public List<ChatMessageDto> message(@DestinationVariable("roomId") Long roomId, ChatMessageCreateDto message){
    	log.info("message(message = {})", message);
    	
    	chatService.create(message);
    	
		List<ChatMessageDto> list = chatService.getMessages(roomId);

		return list;
//        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), list);
    }

}