package com.blog.mapper;

import com.blog.model.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("INSERT INTO article (title, content, user_id, category, tags) VALUES (#{title}, #{content}, #{userId}, #{category}, #{tags})")
    @Options(useGeneratedKeys = true, keyProperty = "articleId", keyColumn = "post_id")
    void insertPost(Article article);

    @Select("SELECT post_id AS articleId, title, content, user_id AS userId, created, last_modified AS lastModified, category, tags, status, view_count AS viewCount " +
            "FROM article WHERE post_id = #{postId}")
    Article selectPostById(Long postId);

    @Select("<script>" +
            "SELECT post_id AS articleId, title, content, user_id AS userId, created, last_modified AS lastModified, category, tags, status, view_count AS viewCount " +
            "FROM article " +
            "WHERE user_id = #{userId} " +
            "<if test='sortOrder != null'> " +
            "ORDER BY created ${sortOrder} " +
            "</if> " +
            "LIMIT #{limit} OFFSET #{offset} " +
            "</script>")
    List<Article> selectPostsByUserId(@Param("userId") Long userId,
                                      @Param("limit") int limit,
                                      @Param("offset") int offset,
                                      @Param("sortOrder") String sortOrder);

    @Select("SELECT post_id AS articleId, title, content, user_id AS userId, created, last_modified AS lastModified, category, tags, status, view_count AS viewCount " +
            "FROM article WHERE post_id = #{articleId} AND user_id = #{userId}")
    Article selectPostByIdAndUserId(@Param("articleId") Long articleId, @Param("userId") Long userId);

    @Update("UPDATE article SET title = #{title}, content = #{content}, category = #{category}, tags = #{tags}, status = #{status}" +
            "WHERE post_id = #{postId} AND user_id = #{userId}")
    int updatePost(@Param("postId") Long postId, @Param("userId") Long userId, @Param("title") String title, @Param("content") String content, @Param("category") String category, @Param("tags") String tags, @Param("status") String status);

    @Delete("DELETE FROM article WHERE post_id = #{postId} AND user_id = #{userId}")
    int deletePost(@Param("postId") Long postId, @Param("userId") Long userId);

}
