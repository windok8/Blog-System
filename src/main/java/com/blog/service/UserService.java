package com.blog.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blog.dto.AuthDto;
import com.blog.mapper.UserMapper;
import com.blog.model.User;
import com.blog.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordUtil passwordUtil;

    public List<String> existUserByUsernameOrEmail(String username, String email) {
        return userMapper.existUserByUsernameOrEmail(username, email);
    }

    public void registerAuth(AuthDto authDto) {
        User user = new User();
        user.setUsername(authDto.getUsername());
        user.setPassword(passwordUtil.generatePassword(authDto.getPassword(), authDto.getEmail()));
        user.setEmail(authDto.getEmail());
        user.setFirstName(authDto.getFirstName());
        user.setLastName(authDto.getLastName());
        user.setProfilePictureUrl(authDto.getProfilePictureUrl());
        user.setBio(authDto.getBio());
        userMapper.insert(user);

    }

    public User getUserByUsernaem(String username) {
        return userMapper.selectByUsername(username);
    }

    public User getUserById(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
