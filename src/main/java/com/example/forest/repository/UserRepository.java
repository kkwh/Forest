package com.example.forest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.dto.stats.UserJoinDto;
import com.example.forest.model.Post;
import com.example.forest.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

    List<Post> findByOrderByIdDesc();
    //로그인할떄 찾기 
    User findByLoginId(@Param("loginId") String loginId);

   
    
   
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

    @Transactional
    @Query("select u from User u "
    		+ " where lower(u.nickname) LIKE lower('%' || :keyword || '%') "
    		+ " order by u.nickname")
    List<User> findAllOrderByNicknameDesc(@Param("keyword") String keyword);

    @Transactional
    @Query("select u from User u "
    		+ " where lower(u.nickname) LIKE lower('%' || :keyword || '%')") 
    List<User> findAllByKeyword(@Param("keyword") String keyword);
    
   @Query(" select u.id from User u "
           + " where u.loginId = :loginId ")
    Long findUserIdByNickname(@Param("loginId") String loginId);
   
   @Query(" select u from User u where u.loginId = nickname ")
   User findUserByNickname(@Param("nickname") String nickname);
    
   @Query("select new com.example.forest.dto.stats.UserJoinDto("
   		+ " 	to_char(u.createdTime, 'yyyy-mm-dd') AS startDate,"
   		+ "		count(u.id) AS count "
   		+ " ) "
   		+ " from User u "
   		+ " group by to_char(u.createdTime, 'yyyy-mm-dd') "
   		+ " order by to_char(u.createdTime, 'yyyy-mm-dd') ")
   List<UserJoinDto> getUserJoinStats();

  
   
    
}
