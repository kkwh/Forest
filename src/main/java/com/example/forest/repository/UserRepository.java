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

    //유저 이메일 중복확인
    @Query("select u from User u where u.email = :email")
    User selectUserByEmail(@Param("email") String email);
    
    //유저 이메일 있나 확인.(아이디 찾을 떄 확인)
    @Query("select u from User u where u.email = :email")
    User findLoginIdByEmail(@Param("email")  String email);

    //DB에 아이디와 이메일이 있는 유저 찾기 select * from USERS where email='sunna56@naver.com' and login_id='qq';
    @Query("select u from User u where u.email = :email and u.loginId= :loginId")
    User findPwByIdEmail(@Param("email") String email,@Param("loginId")  String loginId);

    
    
    
    

    

   

   
    
}
