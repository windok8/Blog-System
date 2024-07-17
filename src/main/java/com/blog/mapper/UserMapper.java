package com.blog.mapper;

import com.blog.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    @Select("(SELECT 'usernmae' AS type FROM users WHERE username = #{username} LIMIT 1) UNION " +
            "(SELECT 'email' AS type FROM users WHERE email = #{email} LIMIT 1)")
    List<String> existUserByUsernameOrEmail(@Param("username") String username, @Param("email") String email);


    @Insert("INSERT INTO users (username, password, email, first_name, last_name, profile_picture_url, bio, created, last_modified) " +
            "VALUES (#{username}, #{password}, #{email}, #{firstName}, #{lastName}, #{profilePictureUrl}, #{bio}, NOW(), NOW())")
    void insert(User user);

    @Select("SELECT user_id AS userId, username, password, email, created, last_modified AS lastModified, first_name AS firstName, last_name AS lastName, profile_picture_url AS profilePictureUrl, bio FROM users WHERE username = #{username}")
    User selectByUsername(String username);

    @Select("SELECT user_id AS userId, username, password, email, created, last_modified AS lastModified, first_name AS firstName, last_name AS lastName, profile_picture_url AS profilePictureUrl, bio FROM users WHERE user_id = #{userId}")
    User selectByPrimaryKey(Long userId);
}
