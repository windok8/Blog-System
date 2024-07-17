package com.blog.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private LocalDateTime created;
    private LocalDateTime lastModified;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private String bio;
}
