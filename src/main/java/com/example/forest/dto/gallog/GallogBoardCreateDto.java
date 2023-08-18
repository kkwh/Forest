package com.example.forest.dto.gallog;


import com.example.forest.model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GallogBoardCreateDto {
    
    long id;
    private User user; //유저 아이디, 닉네임. 
    
   
}
