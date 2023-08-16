package com.example.forest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.board.BoardRankDto;
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
    private final ReplyRepository replyRepository;
    private final ReReplyRepository reReplyRepository;
    
    /**
	 * 랜드에 게시되어 있는 글들을 최신순(내림차순)으로 보여주는 메서드 
	 * @return 
	 */
    @Transactional(readOnly = true)
    public List<Post> read() {
              
        return postRepository.findByOrderByIdDesc();
    }
    
    /**
	 * DB POSTS 테이블에 엔터티를 insert. 게시글을 작성하는 메서드
	 * @param dto
	 * @return
	 */
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
    
    /**
	 * 해당 id의 게시글을 상세 조회하는 메서드
	 * @param id
	 * @return
	 */
    @Transactional(readOnly = true)
    public Post read(Long id) {
        log.info("read(id={})", id);
        
        return postRepository.findById(id).orElseThrow();
    }
    
    /**
	 * 게시글을 수정(업데이트)하는 메서드
	 * @param dto
	 */
    @Transactional // (readOnly = true )
    public void update(PostUpdateDto dto) {
        log.info("update(dto={})", dto);

        Post entity = postRepository.findById(dto.getId()).orElseThrow();
        entity.update(dto);
        postRepository.saveAndFlush(entity);

    }
    
    /**
	 * 게시글을 삭제하는 메서드
	 * @param id
	 */
    public void delete(Long postId, Long userId ) {
        log.info("delete(postId={}, userId={})", postId, userId);
        
        Post post = postRepository.findById(postId).orElseThrow();
        log.info("post = {}", post);
        
        List<Reply> replies = new ArrayList<>();
        
        List<Reply> reps = boardRepository.findAllRepliesByPost(post);
        for(Reply r : reps) {
            replies.add(r);
        }
        
        
        // 게시물에 작성된 대댓글 삭제
        for(Reply reply : replies) {
            reReplyRepository.deleteByReply(reply);
        }
        
        // 게시물에 작성된 댓글 삭제
        replyRepository.deleteByPost(post);
                       
        postRepository.deleteById(postId);
        
    }
    
    /**
	 * 넘겨받은 검색타입과 키워드로 게시글을 검색하는 메서드 
	 * @param dto 
	 * @param pageable
	 * @return
	 */
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
    
    /**
	 * 게시글 조회수의 증가를 반영하여 보여주는 메서드
	 * @param postId
	 * @return
	 */
    public int increaseViewCount(Long postId) {
    	log.info("increaseViewCount(postId={})", postId);
    	
        Post post = postRepository.findById(postId).orElseThrow();
                                  
        post.setPostViews(post.getPostViews() + 1);
        postRepository.save(post);
        return post.getPostViews();
    }
    
    /**
	 * 게시판에서 전체 게시글들을 보여주는 메서드 (POST + 좋아요 수)
	 * @param boardId
	 * @param pageable
	 * @return
	 */
    @Transactional(readOnly = true)
    public Page<PostWithLikesCount> findAllPostsWithLikesCount(Long boardId, Pageable pageable) {              
        return postRepository.findAllPostsWithLikesCount(boardId, pageable);
    }
    
    /**
	 * 게시판에서 인기 게시글들을 보여주는 메서드 (좋아요와 싫어요의 차이가 5 이상인 게시물)
	 * @param boardId
	 * @param pageable
	 * @return
	 */
    public Page<PostWithLikesCount2> findPostsByLikesDifference(Long boardId, Pageable pageable) {
        return postRepository.findAllPostsWithLikesDifference(boardId, pageable);
    }
    
    /**
	 * 게시판에서 공지 게시글들을 보여주는 메서드
	 * @param boardId
	 * @param pageable
	 * @return
	 */
    @Transactional(readOnly = true)
    public Page<PostWithLikesCount> findAllPostsWithLikesCountWhenNotice(Long boardId, Pageable pageable) {              
        return postRepository.findAllPostsWithLikesCountWhenNotice(boardId, pageable);
    }
    
    /**
	 * 게시판에서 말머리가 일반인 게시글을 보여주는 메서드
	 * @param boardId
	 * @param pageable
	 * @return
	 */
    @Transactional(readOnly = true)
    public Page<PostWithLikesCount> findAllPostsWithLikesCountWhenNormal(Long boardId, Pageable pageable) {              
        return postRepository.findAllPostsWithLikesCountWhenNormal(boardId, pageable);
    }
    
    /**
	 * 게시판에서 말머리 키워드를 넘겨받아서 해당 키워드의 게시글들을 보여주는 메서드
	 * @param boardId
	 * @param postType
	 * @param pageable
	 * @return
	 */
    @Transactional(readOnly = true)
    public Page<PostWithLikesCount> findAllPostsWithLikesCountByType(Long boardId, String postType, Pageable pageable) {
        return postRepository.findAllPostsWithLikesCountByType(boardId, postType, pageable);
    }
    
    public Long findRankByLandId(Long landId, String grade) {
        List<BoardRankDto> ranks = boardRepository.findTop10Boards(grade);
        for (BoardRankDto rank : ranks) {
            if (rank.getId().equals(landId) && rank.getBoardRank() <= 10) {
                return rank.getBoardRank();
            }
        }
        return -1L; // 랭킹이 10위 밖이거나 해당 랜드가 없는 경우
    }
    
    public Long findBoardIdByPostId(Long postId) {
        return postRepository.findBoardIdByPostId(postId);
    }
    
    
}