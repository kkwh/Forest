package com.example.forest.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forest.model.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

}
