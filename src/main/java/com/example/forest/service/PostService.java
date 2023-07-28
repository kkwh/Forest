package com.example.forest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.model.Post;
import com.example.forest.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    
    // 생성자를 사용한 의존성 주입:
    private final PostRepository postRepository;
    
    // DB POSTS 테이블에서 전체 검색한 결과를 리턴:
    @Transactional(readOnly = true)
    public List<Post> read() {
              
        return postRepository.findByOrderByIdDesc();
    }
    
//    // DB POSTS 테이블에 엔터티를 삽입(insert):
//    public Post create(PostCreateDto dto) {
//        log.info("create(dto={})", dto);
//        
//        // DTO를 Entity로 변환:
//        Post entity = dto.toEntity();
//        log.info("entity={}", entity);
//        
//        postRepository.save(entity);
//        log.info("entity={}", entity);
//        
//        return entity;
//    }
//    
//    @Transactional(readOnly = true)
//    public Post read(Long id) {
//        log.info("read(id={})", id);
//        
//        return postRepository.findById(id).orElseThrow();
//    }
//    
//    // DB POSTS 테이블에 엔터티 업데이트:
//    
//    @Transactional // (readOnly = true ) DB의 값을 변경하지 않을 시 설정 // (1)
//    public void update(PostUpdateDto dto) {
//        log.info("update(dto={})", dto);
//        
//        // (1) 메서드에 @Transactional 애너테이션을 설정하고,
//        // (2) DB에서 엔터티를 검색하고,
//        // (3) 검색한 엔터티를 수정하면,
//        // 트랜잭션이 끝나는 시점에 DB update가 자동으로 수행됨!
//        
//        Post entity = postRepository.findById(dto.getId()).orElseThrow(); // (2)
//        entity.update(dto); // (3)
//        // postRepository.saveAndFlush(entity);
//
//    }
//    
//    // DB POSTS 삭제 기능
//    public void delete(Long id) {
//        log.info("delete(id={})", id);
//        
//        postRepository.deleteById(id);
//        
//    }
//    
//    @Transactional(readOnly = true)
//    public List<Post> search(PostSearchDto dto) {
//        log.info("search(dto={})", dto);
//        
//        List<Post> list = null;
//        switch (dto.getType()) {
//        case "t":
//            list = postRepository.findByTitleContainsIgnoreCaseOrderByIdDesc(dto.getKeyword());
//            break;
//        case "c":
//            list = postRepository.findByContentContainsIgnoreCaseOrderByIdDesc(dto.getKeyword());
//            break;
//        case "tc":
//            list = postRepository.searchByKeyword(dto.getKeyword());
//            break;
//        case "a":
//            list = postRepository.findByAuthorContainsIgnoreCaseOrderByIdDesc(dto.getKeyword());
//            break;
//            
//        }
//        
//        return list;
//    }
    
    
    
}