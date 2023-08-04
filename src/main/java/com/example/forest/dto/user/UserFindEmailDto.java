package com.example.forest.dto.user;

import lombok.Data;

@Data
public class UserFindEmailDto {
    
    private String loginId;
    private String email;
    private String password;
}
