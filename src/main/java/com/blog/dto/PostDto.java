package com.blog.dto;

import lombok.Data;

@Data
public class PostDto {

    private String title;
    private String content;
    private String category;
    private String tags;
    private String status;

}
