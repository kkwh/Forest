package com.example.forest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forest.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginId(String loginId);

   
    
}
