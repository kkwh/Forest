package com.example.forest.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.post.PostCreateDto;
import com.example.forest.dto.post.PostSearchDto;
import com.example.forest.dto.post.PostUpdateDto;
import com.example.forest.dto.post.PostWithLikesCount;
import com.example.forest.dto.post.PostWithLikesCount2;
import com.example.forest.model.Board;
import com.example.forest.model.Post;
import com.example.forest.model.Reply;
import com.example.forest.model.User;
import com.example.forest.repository.BoardRepository;
import com.example.forest.repository.PostRepository;
import com.example.forest.repository.ReReplyRepository;
import com.example.forest.repository.ReplyRepository;
import com.example.forest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    
    // 생성자를 사용한 의존성 주입:
	private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    
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
        log.info("create(dto={})", dto);
        
        Board board = boardRepository.findById(dto.getBoardId()).orElseThrow();
        entity.setBoard(board);
        log.info("entity-board={}", entity);
        
        if(dto.getUserId() != 0) { // userId == 0 (null, 익명)
        	User user = userRepository.findById(dto.getUserId()).orElseThrow();
            entity.setUser(user);
            log.info("entity-user={}", entity);
        }
               
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
    public Page<PostWithLikesCount> search(PostSearchDto dto, Pageable pageable) {
        log.info("search(dto={})", dto);
        
        Page<PostWithLikesCount> list = null;
        switch (dto.getType()) {
        case "t":
            list = postRepository.findByPostTitleContainsIgnoreCaseOrderByIdDesc(dto.getKeyword(), dto.getBoardId(), pageable);
            break;
        case "c": 
            list = postRepository.findByPostContentContainsIgnoreCaseOrderByIdDesc(dto.getKeyword(), dto.getBoardId(), pageable); 
            break; 
        case "tc": 
            list = postRepository.findByTitleContainsIgnoreCaseOrContentContainsIgnoreCaseOrderByIdDesc(dto.getKeyword(), dto.getKeyword(), dto.getBoardId(), pageable); 
            break; 
        case "a": 
            list = postRepository.findByPostNicknameContainsIgnoreCaseOrderByIdDesc(dto.getKeyword(), dto.getBoardId(), pageable); 
            break;
            
        }
        
        return list;
    }
    
    // 조회수
    public int increaseViewCount(Long postId) {
    	log.info("increaseViewCount(postId={})", postId);
    	
        Post post = postRepository.findById(postId).orElseThrow();
                                  
        post.setPostViews(post.getPostViews() + 1);
        postRepository.save(post);
        return post.getPostViews();
    }
    
    // POST + 좋아요 수
    @Transactional(readOnly = true)
    public Page<PostWithLikesCount> findAllPostsWithLikesCount(Long boardId, Pageable pageable) {              
        return postRepository.findAllPostsWithLikesCount(boardId, pageable);
    }
    
    // 인기글 조회 (좋아요와 싫어요의 차이가 5 이상인 게시물)
    public Page<PostWithLikesCount2> findPostsByLikesDifference(Long boardId, Pageable pageable) {
        return postRepository.findAllPostsWithLikesDifference(boardId, pageable);
    }
    
    // 공지글(NOTICE) 조회
    @Transactional(readOnly = true)
    public Page<PostWithLikesCount> findAllPostsWithLikesCountWhenNotice(Long boardId, Pageable pageable) {              
        return postRepository.findAllPostsWithLikesCountWhenNotice(boardId, pageable);
    }
    
    // 일반글 조회
    @Transactional(readOnly = true)
    public Page<PostWithLikesCount> findAllPostsWithLikesCountWhenNormal(Long boardId, Pageable pageable) {              
        return postRepository.findAllPostsWithLikesCountWhenNormal(boardId, pageable);
    }
    
    public Long findBoardIdByPostId(Long postId) {
        return postRepository.findBoardIdByPostId(postId);
    }
    
    
}