package com.example.forest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.forest.model.Blog;

public interface GardenRepsitory extends JpaRepository<Blog, Long> {

    @Transactional
    @Query("select b from Blog b " + " where b.user.loginId = :loginId ")
    Blog findByUserLoginId(@Param("loginId") String loginId );

    


    
}
