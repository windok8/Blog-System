package com.blog.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
@Slf4j
public class PasswordUtil {

    public static String generatePassword(String password,String salt) {
        return DigestUtils.md5DigestAsHex(addSalt(password,salt).getBytes());
    }

    public static Boolean checkPassword(String password,String salt,String oldPassword) {
        log.info("password={},salt={},oldPassword={}",password,salt,oldPassword);
        String m5Password = DigestUtils.md5DigestAsHex(addSalt(password,salt).getBytes());
        log.info("m5Password={}",m5Password);
        if (!oldPassword.equals(m5Password)) return false;
        return true;
    }

    public static String addSalt(String password,String salt) {
        StringBuffer result = new StringBuffer();
        result.append(password);
        result.append("_");
        result.append(salt);
        return result.toString();
    }


}
