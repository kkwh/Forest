package com.example.forest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forest.model.Post;
import com.example.forest.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginId(String loginId);


   
    
}
