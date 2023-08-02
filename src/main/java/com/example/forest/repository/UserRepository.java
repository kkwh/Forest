package com.example.forest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.forest.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    //로그인할떄 찾기
    User findByLoginId(@Param("loginId") String loginId);

    //유저 아이디 create
   
    //유저 아이디 중복확인
    @Query("select u from User u where u.loginId = :loginId")
    User selectUserByLoginId(@Param("loginId") String loginId);
    
    //유저 닉네임 중복확인 
    @Query("select u from User u where u.nickname = :nickname ")
    User selectUserByNickname(@Param("nickname") String nickname);

    //유저 이메일 확인
    @Query("select u from User u where u.email = :email")
    User selectUserByEmail(String email);
    
    //유저 이메일 확인 (나중에)
    

    

   

   
    
}
