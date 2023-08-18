package com.example.forest.dto.user;



import com.example.forest.model.ImageFile;

import lombok.Data;

@Data
public class UserSignUpDto {

    private long id;
    private ImageFile imageId;
    private String loginId;

    private String nickname;

    private String password;

    private String email;
    
    

}
