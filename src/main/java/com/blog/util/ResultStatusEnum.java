package com.blog.util;

import lombok.Getter;

public enum ResultStatusEnum {

    //  通用状态码
    SUCCESS(200, "成功"),
    FAIL(400, "失败"),
    //  用户相关状态码
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_USERNAME_EXISTS(1002, "用户名已存在"),
    USER_EMAIL_EXISTS(1003, "邮箱已存在"),
    //  文章相关状态码
    POST_NOT_FOUND(1004, "文章不存在"),
    //  其他业务相关状态码
    ;


    @Getter
    private final int code;

    @Getter
    private final String value;

    private ResultStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
