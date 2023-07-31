package com.example.forest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.post.PostCreateDto;
import com.example.forest.dto.post.PostSearchDto;
import com.example.forest.dto.post.PostUpdateDto;
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
    
    // DB POSTS 테이블에 엔터티를 삽입(insert):
    public Post create(PostCreateDto dto) {
        log.info("create(dto={})", dto);
        
        // DTO를 Entity로 변환:
        Post entity = dto.toEntity();
        log.info("entity={}", entity);
        
        postRepository.save(entity);
        log.info("entity={}", entity);
        
        return entity;
    }
    
    @Transactional(readOnly = true)
    public Post read(Long id) {
        log.info("read(id={})", id);
        
        return postRepository.findById(id).orElseThrow();
    }
    
    // DB POSTS 테이블에 엔터티 업데이트:    
    @Transactional // (readOnly = true )
    public void update(PostUpdateDto dto) {
        log.info("update(dto={})", dto);

        Post entity = postRepository.findById(dto.getId()).orElseThrow();
        entity.update(dto);
        postRepository.saveAndFlush(entity);

    }
    
    // DB POSTS 삭제 기능
    public void delete(Long id) {
        log.info("delete(id={})", id);
        
        postRepository.deleteById(id);
        
    }
    
    @Transactional(readOnly = true)
    public List<Post> search(PostSearchDto dto) {
        log.info("search(dto={})", dto);
        
        List<Post> list = null;
        switch (dto.getType()) {
        case "t":
           list = postRepository.findByPostTitleContainsIgnoreCaseOrderByIdDesc(dto.getKeyword());
            break;
        case "c":
            list = postRepository.findByPostContentContainsIgnoreCaseOrderByIdDesc(dto.getKeyword());
            break;
        case "tc":
            list = postRepository.searchByKeyword(dto.getKeyword());
            break;
        case "a":
           list = postRepository.findByPostNicknameContainsIgnoreCaseOrderByIdDesc(dto.getKeyword());
            break;
            
        }
        
        return list;
    }
    
    public int increaseViewCount(Long postId) {
    	log.info("increaseViewCount(postId={})", postId);
    	
        Post post = postRepository.findById(postId).orElseThrow();
                                  
        post.setPostViews(post.getPostViews() + 1);
        postRepository.save(post);
        return post.getPostViews();
    }
    
    
    
}