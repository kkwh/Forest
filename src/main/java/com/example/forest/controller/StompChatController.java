package com.example.forest.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.forest.dto.chat.ChatMessageCreateDto;
import com.example.forest.dto.chat.ChatMessageDto;
import com.example.forest.model.ChatMessage;
import com.example.forest.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StompChatController {
	
	private final ChatService chatService;
	
	private final SimpMessagingTemplate template;
	
//	@MessageMapping(value = "/chat/enter")
//    public void enter(ChatMessageCreateDto message){
//		log.info("enter()");
//		
//		List<ChatMessageDto> list = chatService.getMessages(message.getRoomId());
//		
//        message.setMessage(message.getLoginId() + "님이 채팅방에 참여하였습니다.");
//        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), list);
//    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/chatting")
    public ChatMessageDto message(ChatMessageCreateDto message) throws Exception{
    	log.info("message(message = {})", message);
    	
    	Thread.sleep(100);
    	
    	return new ChatMessageDto(message.getMessage(), message.getNickname());
    }

}