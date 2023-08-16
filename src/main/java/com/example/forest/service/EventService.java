package com.example.forest.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.event.EventCreateDto;
import com.example.forest.dto.event.EventSearchDto;
import com.example.forest.dto.event.EventUpdateDto;
import com.example.forest.model.Event;
import com.example.forest.repository.EventRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    
    // 생성자를 사용한 의존성 주입:
    private final EventRepository eventRepository;
    
    // DB EVENT 테이블에서 전체 검색한 결과를 리턴: 
    // Event 목록 불러오기
    @Transactional(readOnly = true)
    public Page<Event> read(Pageable pageable){
        log.info("read()");
        
        return eventRepository.findAll(pageable);
    }
    
    // Event 상세보기
    @Transactional(readOnly = true)
    public Event read(Long id) {
        log.info("read(id={})", id);
        
        return eventRepository.findById(id).orElseThrow();
    }
    
    
    
    // DB EVENT 테이블에 엔터티를 삽입(insert):
    // Event 게시글 생성
    public Event create(EventCreateDto dto) {
        log.info("created(dto={})", dto);
        
        // DTO를 Entity로 변환:
        Event entity = dto.toEntity();
        log.info("entity={}", entity);
        
        // DB 테이블에 저장(insert)
        eventRepository.save(entity);
        log.info("entity={}", entity);
        
        return entity;
        
    }
    
    // Event 게시글 삭제
    public void delete(Long id) {
        log.info("delete(id={})", id);
        
        eventRepository.deleteById(id);
        
    }
    
    // Event 게시글 수정
    @Transactional
    public void update(EventUpdateDto dto) {
        log.info("update=(dto={})", dto);
        
        // (1) 메서드에 @Transactional 애너테이션을 설정하고,
        // (2) DB에서 엔터티를 검색하고, 
        // (3) 검색한 엔터티를 수정하면,
        // 트랙잭션이 끝나는 시점에 DB update가 자동으로 수행됨!
        Event entity = eventRepository.findById(dto.getId()).orElseThrow();
        entity.update(dto);
    }
    
    @Transactional(readOnly = true)
    public Page<Event> search(EventSearchDto dto, Pageable pageable) {
        log.info("search(dto={})", dto);
        
        Page<Event> list = null;
        switch (dto.getType()) {
        case "t":
            list = eventRepository.findByTitleContainsIgnoreCaseOrderByIdDesc(dto.getKeyword(), pageable);
            break;
        case "c":
            list = eventRepository.findByContentContainsIgnoreCaseOrderByIdDesc(dto.getKeyword(), pageable);
            break;
        case "tc":
            list = eventRepository.searchByKeyword(dto.getKeyword(), pageable);
            break;
        case "a":
            list = eventRepository.findByAuthorContainsIgnoreCaseOrderByIdDesc(dto.getKeyword(), pageable);
            break;
        }
        
        return list;
    }

}
