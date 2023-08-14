package com.example.forest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.forest.dto.chat.ChatMessageCreateDto;
import com.example.forest.dto.chat.ChatMessageDto;
import com.example.forest.dto.chat.ChatRoomDto;
import com.example.forest.model.ChatMessage;
import com.example.forest.model.ChatRoom;
import com.example.forest.model.User;
import com.example.forest.repository.ChatMessageRepository;
import com.example.forest.repository.ChatRoomRepository;
import com.example.forest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
	
	private final ChatRoomRepository roomRepository;
	private final ChatMessageRepository messageRepository;
	private final UserRepository userRepository;

	/**
	 * 모든 채팅방을 리스트로 불러오는 메서드
	 * @return
	 */
	public List<ChatRoomDto> findAllRooms() {
		log.info("findAllRooms()");
		
		return roomRepository.findAllRooms();
	}
	
	/**
	 * 검색된 키워드가 포함된 채팅방을 불러오는 메서드
	 * @param keyword
	 * @return
	 */
	public List<ChatRoomDto> findAllRoomsByKeyword(String keyword) {
		log.info("findAllRooms()");
		
		return roomRepository.findAllRoomsByKeyword(keyword);
	}
	
	/**
	 * 특정 채팅방을 불러오는 메서드
	 * @param id
	 * @return
	 */
	public ChatRoom getRoom(long id) {
		log.info("getRoom(id = {})", id);
		
		ChatRoom room = roomRepository.findById(id).orElseThrow();
		
		return room;
	}
	
	/**
	 * 채팅방을 개설하는 메서드
	 * @param name
	 * @param loginId
	 */
	public void create(String name, String loginId) {
		log.info("create()");
		
		User creator = userRepository.findByLoginId(loginId);
		
		ChatRoom entity = ChatRoom.builder()
				.name(name)
				.creatorId(creator.getId())
				.build();
		log.info("entity = {}", entity);
		
		roomRepository.save(entity);
	}
	
	/**
	 * 채팅 메세지를 저장하는 메서드
	 * @param dto
	 */
	public void create(ChatMessageCreateDto dto) {
		User user = userRepository.findByLoginId(dto.getLoginId());
		
		ChatMessage entity = ChatMessage.builder()
				.content(dto.getMessage())
				.roomId(dto.getRoomId())
				.senderId(user.getId())
				.build();
		
		log.info("create(entity = {})", entity);
		
		messageRepository.save(entity);
	}
	
	/**
	 * 채팅방에 있는 메세지들을 불러오는 메서드
	 * @param roomId
	 * @return
	 */
	public List<ChatMessageDto> getMessages(long roomId) {
		log.info("getMessages(id = {})", roomId);
		
		return messageRepository.getAllByRoomId(roomId);
	}

}
