package com.example.forest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forest.model.Diary;
import com.example.forest.service.GardenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gardenDiary")
public class GardenDiaryRestController {
    
    private final GardenService gardenService;
    
    /**
    @GetMapping("/all/{gardenId}")
    public ResponseEntity<List<Diary>> all(@PathVariable long gardenId){
        log.info("all(gardenId={})",gardenId);
        List<Diary> diarylist = gardenser
    }
    **/
}
