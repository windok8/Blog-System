package com.blog.util;

import com.blog.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: windok
 * @date: 2024/07/16
 */
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public Claims extractALlClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public JwtInfo parseToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        if(claims!=null){
            JwtInfo jwtInfo = new JwtInfo();
            Map<String,Object> map = new HashMap<>();
            map.put("userId",claims.get("userId"));
            jwtInfo.setMap(map);
            jwtInfo.setSubject(claims.getSubject());
            jwtInfo.setIssuedAt(claims.getIssuedAt().getTime());
            return jwtInfo;
        }
        return null;
    }

    public String greateToken(User user) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",user.getUserId());
        return createToken(map,user.getUsername());
    }

    public String createToken(Map<String,Object> map,String subject) {
        String jwt = Jwts.builder()
                .addClaims(map)
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, secret)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setIssuedAt(new Date())
                .compact();
        return jwt;
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        Claims claims = extractALlClaims(token);
        return claims.getIssuedAt();
    }



}
