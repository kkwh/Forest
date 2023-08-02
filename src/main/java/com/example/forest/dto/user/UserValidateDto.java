package com.example.forest.dto.user;

import lombok.Data;

@Data
public class UserValidateDto {
    
    private String loginId;
    private String nickname;
    private String email;
}
