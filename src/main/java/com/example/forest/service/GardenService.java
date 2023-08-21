package com.example.forest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.forest.dto.gallog.DiaryCreateDto;
import com.example.forest.model.Blog;
import com.example.forest.model.Board;
import com.example.forest.model.Diary;
import com.example.forest.model.Post;
import com.example.forest.model.Reply;
import com.example.forest.model.User;
import com.example.forest.repository.BoardRepository;
import com.example.forest.repository.DiaryRepository;
import com.example.forest.repository.GardenRepsitory;
import com.example.forest.repository.PostRepository;
import com.example.forest.repository.ReplyRepository;
import com.example.forest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class GardenService {

    private final GardenRepsitory gardenRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final DiaryRepository diaryRepository;
    public Blog read(String loginId) {
        
        Blog blog = gardenRepository.findByUserLoginId(loginId);
        
        return blog;
    }

    public Long findUserIdByNickname(String nickname) {
         
        log.info("서비스={}",nickname);
        return userRepository.findUserIdByNickname(nickname);
    }

    

    public List<Post> findByuserIdPosts(Long userId) {
        User entity = userRepository.findById(userId).orElseThrow();
        log.info("포스트리스트 서비스={}", userId);
        log.info("서비스={}", entity);
        
        return postRepository.findByOrderByuserIdDesc(entity);
    }

    public List<Board> findByuserIdBoards(Long userId) {
        User entity = userRepository.findById(userId).orElseThrow();
        log.info("보드리스트서비스={}", userId);
        log.info("보드리스트서비스={}", entity);
        return boardRepository.findByOrderByuserIdDesc(entity);
    }

    public List<Reply> findByuserIdReplys(Long userId) {

        return replyRepository.findByOrderByuserIdDesc(userId);
    }

    public Diary create(DiaryCreateDto dto) {
        log.info("create(dto={}), dto");
       
        Blog blog = gardenRepository.findById(dto.getBlogId()).orElseThrow();
        
        long writerId = userRepository.findByLoginId(dto.getLoginId()).getId();
                       
        Diary entity = Diary.builder()
                .content(dto.getContent())
                .blog(blog)
                .writerId(writerId)
                .build();
        diaryRepository.save(entity);
        
        return entity;
    }



}
