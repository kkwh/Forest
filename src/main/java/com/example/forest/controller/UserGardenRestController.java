package com.example.forest.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forest.dto.gallog.DiaryCreateDto;
import com.example.forest.model.Diary;
import com.example.forest.service.GardenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/garden/gardenmain")
public class UserGardenRestController {
    
    private final GardenService gardenService;

    @PostMapping
    public ResponseEntity<Diary> create(@RequestBody DiaryCreateDto dto){
        log.info("create(dto={})",dto);
        
        Diary diary = gardenService.create(dto);
        log.info("diary={}", diary);
        
        return ResponseEntity.ok(diary);
    }
    
}
