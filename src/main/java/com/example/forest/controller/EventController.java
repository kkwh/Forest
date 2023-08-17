package com.example.forest.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.forest.dto.event.EventCreateDto;
import com.example.forest.dto.event.EventSearchDto;
import com.example.forest.dto.event.EventUpdateDto;
import com.example.forest.model.Event;
import com.example.forest.service.EventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {
    
    private final EventService eventService;
    
    @GetMapping
    public String read(Model model, 
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("read()");
        
        Page<Event> list = eventService.read(pageable);
        
        int nowPage = list.getPageable().getPageNumber() + 1; // 현재페이지
        int maxPage = list.getTotalPages();

        int visiblePageCount = 5; // 한 번에 표시될 페이지 번호의 갯수
        int halfVisiblePageCount = visiblePageCount / 2;

        int startPage = Math.max(nowPage - halfVisiblePageCount, 1);
        int endPage = Math.min(startPage + visiblePageCount - 1, maxPage);
        
        model.addAttribute("event", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        
        return "/event/read";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public void create() {
        log.info("create() GET");
    }
    
    @PreAuthorize("hasRole('ADMIN')")    
    @PostMapping("/create")
    public String create(EventCreateDto dto) {
        log.info("create(dto={}) EVENT", dto);
        
        eventService.create(dto);
        
        return "redirect:/event";
    }
    
    @GetMapping({"/details", "/modify"})
    public void read(Long id, Model model) {
        log.info("read=(id={})", id);
        
        Event event = eventService.read(id);
        model.addAttribute("event", event);
        
//        long viewCount = eventService.viewEventCount(id) -1;
//        model.addAttribute("viewEventCount", viewCount);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public String delete(Long id) {
        log.info("delete(id={})", id);
        
        eventService.delete(id);
        
        return "redirect:/event";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public String update(EventUpdateDto dto) {
        log.info("udpate(dto={})", dto);
        
        eventService.update(dto);
        
        return "redirect:/event/details?id=" + dto.getId();
    }
    
    @GetMapping("/search")
    public String search(EventSearchDto dto, Model model, @PageableDefault(page=0, size=5, sort="id",
            direction=Sort.Direction.DESC) Pageable pageable) {
        log.info("search(dto={})", dto);
        
        // postService의 검색 기능 호출:
        Page<Event> list = eventService.search(dto, pageable);
        
        int nowPage = list.getPageable().getPageNumber() + 1; // 현재페이지 
        int maxPage = list.getTotalPages();
        
        int visiblePageCount = 5; // 한 번에 표시될 페이지 번호의 갯수 
        int halfVisiblePageCount = visiblePageCount / 2;
        int startPage = Math.max(nowPage - halfVisiblePageCount, 1); 
        int endPage = Math.min(startPage + visiblePageCount - 1, maxPage);
        
        model.addAttribute("event", list);
        model.addAttribute("startPage", startPage); model.addAttribute("endPage", endPage);
        
        return "/event/read";
    }

}
