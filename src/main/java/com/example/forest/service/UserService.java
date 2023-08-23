package com.example.forest.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.gallog.GallogBoardListDto;
import com.example.forest.dto.gallog.GallogBoardCreateDto.GallogBoardCreateDtoBuilder;
import com.example.forest.dto.user.UserFindPasswordDto;
import com.example.forest.dto.user.UserInfoUpdateDto;
import com.example.forest.dto.user.UserReplyDto;
import com.example.forest.dto.user.UserSignUpDto;
import com.example.forest.model.Blog;
import com.example.forest.model.Board;
import com.example.forest.model.Post;
import com.example.forest.model.Reply;
import com.example.forest.model.User;
import com.example.forest.repository.GardenRepsitory;
import com.example.forest.repository.BoardRepository;
import com.example.forest.repository.ChatMessageRepository;
import com.example.forest.repository.PostRepository;
import com.example.forest.repository.ReplyRepository;
import com.example.forest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
	
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final GardenRepsitory blogRepository;
    private final ChatMessageRepository messageRepository;
    
    public Long registerUser(UserSignUpDto dto) { //유저 회원가입
        log.info("registerUser(dto={})", dto);
        
        User entity = User.builder()
                .loginId(dto.getLoginId())
                .imageFile(dto.getImageId())
                .nickname(dto.getNickname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .build();
                    
        
        log.info("save 전: entity={}", entity);
        
        User user = userRepository.save(entity); // 디비 집어넣기
       
        log.info("save후 : entity={}", entity);
        
        Blog blog = Blog.builder()
        .user(user)
        .build();
                
        blogRepository.save(blog);
        
        return entity.getId();
    }
    
   
    
    
    public long getUserId(String loginId) {
    	long userId = userRepository.findByLoginId(loginId).getId();
    	
    	return userId;
    }
    
	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		
	    log.info("loadUserByUserLoginId(loginId)",loginId);
	    
	    UserDetails user = userRepository.findByLoginId(loginId);
	    if (user != null) {
	        return user;
	    }
	    throw new UsernameNotFoundException(loginId + " - not found");
	}
	
	public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
	   
    }
	
	public User findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    public int validateLoginId(String loginId) {
        User user = userRepository.selectUserByLoginId(loginId);
       // log.info("서비스도 왔어요");
        if(user == null) {
            return 1;
        }
        return 0;
    }
    
    public int validateLoginNickname(String nickname) {
        User user = userRepository.selectUserByNickname(nickname);
        if(user == null) {
            return 1; //아이디 찾을 때 1 일때만 됨.
            
        }
        return 0; //회원가입 할 때 0이면 회원가입이 됨
    }

    public int validateLoginEmail(String email) {
       User user = userRepository.selectUserByEmail(email);
       if(user == null) {
           return 1;
       }
        return 0;
    }

    public String findLoginIdByEmail(String email) {
        User user = userRepository.findLoginIdByEmail(email);
        if(user == null) {
            return "";
        }
        
        return user.getLoginId();
    }

    public int findPw(String email, String loginId) {
        User user = userRepository.findPwByIdEmail(email,loginId);
        
        if(user == null) {
            return 1; // 1이면 없음
        }
        return 0; //0이면 있음.
    }
    
    @Transactional(readOnly = true)
    public List<User> getUserList(String keyword) {
    	log.info("getUserList()");
    	
    	return userRepository.findAllOrderByNicknameDesc(keyword);
    }
    
    @Transactional(readOnly = true)
    public List<User> getUserListByKeyword(String keyword) {
    	log.info("getUserListByKeyword(keyword = {})", keyword);
    	
    	return userRepository.findAllByKeyword(keyword);
    }

    @Transactional
    public void update(UserInfoUpdateDto dto) {
        log.info("update(dto={})", dto);
        log.info("update서비스왔어요.");
        
        User entity = userRepository.findById(dto.getId()).orElseThrow();
        
        /* userRepository.updateNickname(dto.getNickname(), dto.getId()); */
        
//        User entity = userRepository.findById(dto.getId()).orElseThrow();
        //entity.setNickname(dto.getNickname());
        
        messageRepository.updateUserNickname(entity.getNickname(), dto.getNickname());
       
        entity.update(dto);
        log.info("entity: {}", entity);
        userRepository.saveAndFlush(entity);
        log.info("유저 닉네임 업데이트 완료");
    }

    public void updatepw(UserFindPasswordDto dto) {
        log.info("updatepw(dto={})",dto);
        log.info("updatepw서비스 왔어요.");
        
        User entity = userRepository.findById(dto.getId()).orElseThrow();
        
        entity.updatepw(dto);
        log.info("entity: {}", entity);
        userRepository.saveAndFlush(entity);
        log.info("유저 비밀번호 1234로 바꾸기 완료");
        
    }

    @Transactional(readOnly = true)
    public List<Board> findAllByUser(String name) {
        
        User entity = userRepository.findByLoginId(name);
        List<Board> boards = boardRepository.findAllBoardsByUserOrderByIdDesc(entity);
        
        return boards;
        
       
        
    }
    public List<Post> findAllByUserPost(Long uId) {
        User entity = userRepository.findById(uId).orElseThrow();
        List<Post> posts = postRepository.findAllPostByUserOrderByIdDesc(entity);
        return posts;
    }

    public List<UserReplyDto> findAllByUserReply(long uId) {
     //   User entity = userRepository.findById(uId).orElseThrow();
        List<UserReplyDto> replies = replyRepository.findAllRepliesByUserId(uId);
        return replies;
    }




  



    

   
   
}
