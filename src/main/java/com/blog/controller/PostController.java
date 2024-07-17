package com.blog.controller;

import com.blog.dto.PostDto;
import com.blog.model.Article;
import com.blog.service.PostService;
import com.blog.service.UserService;
import com.blog.util.Result;
import com.blog.util.ResultStatusEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PostService postService;

    @PostMapping
    public Result<?> createPost(@RequestBody PostDto postDto, HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        Article article = new Article();
        article.setTitle(postDto.getTitle());
        article.setUserId(userId);
        article.setContent(postDto.getContent());
        article.setCategory(postDto.getCategory());
        article.setTags(postDto.getTags());
        postService.createPost(article);

        return Result.success(postService.selectPostById(article.getArticleId()));
    }

    @GetMapping
    public Result<?> getPostsByUserId(
            @RequestParam("uid") Long userId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "DESC") String sortOrder) {

        List<Article> posts = postService.getPostsByUserId(userId, page, size, sortOrder);
        if (posts.isEmpty()) return Result.error(ResultStatusEnum.POST_NOT_FOUND.getCode(), ResultStatusEnum.POST_NOT_FOUND.getValue());
        return Result.success(posts);
    }

    @GetMapping("/{id}")
    public Result<?> getPostById(@PathVariable Long id) {
        Article post = postService.selectPostById(id);
        if (post == null) {
            return Result.error(ResultStatusEnum.POST_NOT_FOUND.getCode(), ResultStatusEnum.POST_NOT_FOUND.getValue());
        }
        return Result.success(post);
    }

    @PutMapping("/{id}")
    public Result<?> updatePost(@PathVariable Long id, @RequestBody PostDto postDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Article post = postService.selectPostByIdAndUserId(id, userId);
        if (post == null) {
            return Result.error(ResultStatusEnum.POST_NOT_FOUND.getCode(), ResultStatusEnum.POST_NOT_FOUND.getValue());
        }
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCategory(postDto.getCategory());
        post.setTags(postDto.getTags());
        post.setStatus(postDto.getStatus());
        if (postService.updatePost(id,userId,post)==1) return Result.success(post);
        return Result.error(ResultStatusEnum.FAIL.getCode(), "更新失败");
    }

    @DeleteMapping("/{id}")
    public Result<?> deletePost(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Article post = postService.selectPostByIdAndUserId(id, userId);
        if (post == null) {
            return Result.error(ResultStatusEnum.POST_NOT_FOUND.getCode(), ResultStatusEnum.POST_NOT_FOUND.getValue());
        }
        if (postService.deletePost(id,userId)!=1) return Result.error(ResultStatusEnum.FAIL.getCode(), "删除失败");
        return Result.success("删除成功");

    }


}
