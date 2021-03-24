package com.pzh.zp.utils;

import com.pzh.zp.VO.UserVo;
import com.pzh.zp.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;

public class JWTUtil {
    private static String secret="zhaopeng";
    private static String subject="zp";

    public static String generateToken(UserVo user){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,7);

        if(user==null){
            return null;
        }
       return Jwts.builder()
                .setSubject(subject)
                .claim("id",user.getId())
                .claim("userName",user.getUserName())
                .claim("nickName",user.getNickName())
                .claim("status",user.getStatus())
                .setIssuedAt(new Date())
                .setExpiration(instance.getTime())
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public static Claims getClaimByToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
