package com.blog.service;

import com.blog.mapper.ArticleMapper;
import com.blog.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private ArticleMapper articleMapper;

    public void createPost(Article article) {
        articleMapper.insertPost(article);
    }

    public Article selectPostById(Long postId) {
        return articleMapper.selectPostById(postId);
    }

    public List<Article> getPostsByUserId(Long userId, int page, int size, String sortOrder) {
        int offset = (page - 1) * size;
        return articleMapper.selectPostsByUserId(userId, size, offset, sortOrder);
    }

    public Article selectPostByIdAndUserId(Long id, Long userId) {
        return articleMapper.selectPostByIdAndUserId(id, userId);
    }

    public int updatePost(Long id, Long userId, Article post) {
        return articleMapper.updatePost(id,userId,post.getTitle(),post.getContent(),post.getCategory(),post.getTags(),post.getStatus());
    }

    public int deletePost(Long id, Long userId) {
        return articleMapper.deletePost(id, userId);
    }
}
