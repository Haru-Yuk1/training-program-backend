package com.test.trainingprogrambackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {
    //7天过期
    private static long expire=604800;
    //32位密钥
    private static String secret="abcdfghiabcdfghiabcdfghiabcdfghi";

    //生成token
    public static String generateToken(String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime()+expire*1000);
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    //生成邮箱token
    public static String generateEmailToken(String to,String code) {
        Date now = new Date();
        Date expiration = new Date(now.getTime()+expire*1000);
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(to)
                .claim("code",code)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    //生成手机token

    //解析token
    public static Claims getClaimsByToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }



}
