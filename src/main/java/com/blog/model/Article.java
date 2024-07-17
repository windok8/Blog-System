package com.blog.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Article {

    private Long articleId;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime created;
    private LocalDateTime lastModified;
    private String category;
    private String tags;
    private String status;
    private Integer viewCount;

}
