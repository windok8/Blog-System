package com.blog.util;

import lombok.Data;

import java.util.Map;

@Data
public class JwtInfo {

    Map<String,Object> map;
    String subject;
    Long issuedAt;

}
