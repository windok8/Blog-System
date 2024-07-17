package com.blog.controller;

import com.blog.dto.AuthDto;
import com.blog.dto.LoginInfo;
import com.blog.model.User;
import com.blog.service.UserService;
import com.blog.util.JwtUtils;
import com.blog.util.PasswordUtil;
import com.blog.util.Result;
import com.blog.util.ResultStatusEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public Result<?> registerAuth(@RequestBody AuthDto authDto){
        List<String> existUserByUsernameOrEmail = userService.existUserByUsernameOrEmail(authDto.getUsername(), authDto.getEmail());
        if (!CollectionUtils.isEmpty(existUserByUsernameOrEmail)) {
            if (existUserByUsernameOrEmail.size()==2 || existUserByUsernameOrEmail.get(0).equals("email")) return Result.error(ResultStatusEnum.USER_EMAIL_EXISTS.getCode(), ResultStatusEnum.USER_EMAIL_EXISTS.getValue());
            else return Result.error(ResultStatusEnum.USER_USERNAME_EXISTS.getCode(), ResultStatusEnum.USER_USERNAME_EXISTS.getValue());
        }
        userService.registerAuth(authDto);
        return Result.success(ResultStatusEnum.SUCCESS.getCode(),"注册成功");
    }


    @PostMapping("/login")
    public Result<?> login(@RequestBody AuthDto authDto) {
        User user = userService.getUserByUsernaem(authDto.getUsername());
        if (user == null) {
            return Result.error(ResultStatusEnum.USER_NOT_FOUND.getCode(), ResultStatusEnum.USER_NOT_FOUND.getValue());
        }
        if (!PasswordUtil.checkPassword(authDto.getPassword(),user.getUsername(),user.getPassword())){
            return Result.error(ResultStatusEnum.FAIL.getCode(), "密码错误");
        }
        String jwt = jwtUtils.greateToken(user);
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId(user.getUserId());
        loginInfo.setUsername(user.getUsername());
        loginInfo.setProfilePictureUrl(user.getProfilePictureUrl());
        loginInfo.setToken(jwt);
        return Result.success(loginInfo);
    }

    @GetMapping("/me")
    public Result<?> getCurrentAuth(HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        User user = userService.getUserById(userId);
        if (user == null) {
            return Result.error(ResultStatusEnum.USER_NOT_FOUND.getCode(), ResultStatusEnum.USER_NOT_FOUND.getValue());
        }
        return Result.success(user);
    }

}
