package com.example.forest.dto.event;

import com.example.forest.model.Event;

import lombok.Data;

@Data
public class EventCreateDto {

    private String title;
    private String content;
    private String author;
    
    public Event toEntity() {
        
        return Event.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
    
}
