package com.blog.dto;

import lombok.Data;

@Data
public class AuthDto {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private String bio;

}
