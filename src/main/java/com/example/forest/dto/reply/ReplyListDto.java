package com.example.forest.dto.reply;

import java.util.List;

import com.example.forest.model.Reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyListDto {
    
    private Long userId;
    private List<Reply> list;
    private Long count;

}
