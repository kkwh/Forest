package com.example.forest.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.user.UserSignUpDto;
import com.example.forest.model.User;
import com.example.forest.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
	
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
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
        
        userRepository.save(entity); // 디비 집어넣기
        log.info("save후 : entity={}", entity);
        
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

 
}
