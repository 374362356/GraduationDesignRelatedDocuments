package com.pzh.zp.utils;

import com.pzh.zp.VO.UserVo;
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
                .claim("username",user.getUserName())
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

/*import java.io.IOException;
import java.security.SignatureException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

import com.pzh.zp.VO.UserVo;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.CompressionCodecResolver;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver;
import org.springframework.util.StringUtils;

public class JWTUtil {
    //秘钥
    public static final String SECRET_KEY = "zhaopeng";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final String subject="zp";
    public static final String issuer="token-server";
    public static final Long period=7200L;
    private static CompressionCodecResolver codecResolver = new DefaultCompressionCodecResolver();

    //私有化构造
    private JWTUtil() {
    }

    *//* *
     * @Description  json web token 签发
     * @param id 令牌ID
     * @param subject 用户标识
     * @param userId
     * @param issuer 签发人
     * @param period 有效时间(秒)
     * @param roles 访问主张-角色
     * @param permissions 访问主张-权限
     * @param algorithm 加密算法
     * @Return java.lang.String
     *//*
    public static String issueJWT(Integer id, String userName, String nickName, Integer status, SignatureAlgorithm algorithm) {
        // 当前时间戳
        Long currentTimeMillis = System.currentTimeMillis();
        // 秘钥
        byte[] secreKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        JwtBuilder jwtBuilder = Jwts.builder();
        if (!StringUtils.isEmpty(id)) {
            jwtBuilder.setId(id.toString());
        }
        // 设置签发时间
        jwtBuilder.setIssuedAt(new Date(currentTimeMillis));
        // 设置到期时间
        if (null != period) {
            jwtBuilder.setExpiration(new Date(currentTimeMillis + period*1000));
        }

        if (!StringUtils.isEmpty(userName)) {
            jwtBuilder.claim("userName",userName);
        }
        if (!StringUtils.isEmpty(nickName)) {
            jwtBuilder.claim("nickName",nickName);
        }
        if (!StringUtils.isEmpty(status)) {
            jwtBuilder.claim("status",status);
        }
        // 压缩，可选GZIP
        jwtBuilder.compressWith(CompressionCodecs.DEFLATE);
        // 加密设置
        jwtBuilder.signWith(algorithm,secreKeyBytes);

        return jwtBuilder.compact();
    }

    *//* *
     * @Description  json web token 签发
     * @param subject 用户标识
     * @param userId
     * @param period 有效时间(秒)
     * @Return java.lang.String
     *//*
    public static String issueJWT(UserVo userVo) {
        return issueJWT(userVo.getId(), userVo.getUserName(), userVo.getNickName(), userVo.getStatus(), SignatureAlgorithm.HS256);
    }

    *//**
     * 解析JWT的Payload
     *//*
    public static String parseJwtPayload(String jwt){
        Assert.hasText(jwt, "JWT String argument cannot be null or empty.");
        String base64UrlEncodedHeader = null;
        String base64UrlEncodedPayload = null;
        String base64UrlEncodedDigest = null;
        int delimiterCount = 0;
        StringBuilder sb = new StringBuilder(128);
        for (char c : jwt.toCharArray()) {
            if (c == '.') {
                CharSequence tokenSeq = io.jsonwebtoken.lang.Strings.clean(sb);
                String token = tokenSeq!=null?tokenSeq.toString():null;

                if (delimiterCount == 0) {
                    base64UrlEncodedHeader = token;
                } else if (delimiterCount == 1) {
                    base64UrlEncodedPayload = token;
                }

                delimiterCount++;
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        if (delimiterCount != 2) {
            String msg = "JWT strings must contain exactly 2 period characters. Found: " + delimiterCount;
            throw new MalformedJwtException(msg);
        }
        if (sb.length() > 0) {
            base64UrlEncodedDigest = sb.toString();
        }
        if (base64UrlEncodedPayload == null) {
            throw new MalformedJwtException("JWT string '" + jwt + "' is missing a body/payload.");
        }
        // =============== Header =================
        Header header = null;
        CompressionCodec compressionCodec = null;
        if (base64UrlEncodedHeader != null) {
            String origValue = TextCodec.BASE64URL.decodeToString(base64UrlEncodedHeader);
            Map<String, Object> m = readValue(origValue);
            if (base64UrlEncodedDigest != null) {
                header = new DefaultJwsHeader(m);
            } else {
                header = new DefaultHeader(m);
            }
            compressionCodec = codecResolver.resolveCompressionCodec(header);
        }
        // =============== Body =================
        String payload;
        if (compressionCodec != null) {
            byte[] decompressed = compressionCodec.decompress(TextCodec.BASE64URL.decode(base64UrlEncodedPayload));
            payload = new String(decompressed, io.jsonwebtoken.lang.Strings.UTF_8);
        } else {
            payload = TextCodec.BASE64URL.decodeToString(base64UrlEncodedPayload);
        }
        return payload;
    }

    *//**
     * 验签JWT
     *
     * @param jwt json web token
     *//*
    public static Token parseJwt(String jwt) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt)
                .getBody();
        Token jwtAccount = new Token();
        //令牌ID
        jwtAccount.setId(claims.getId());
        //客户标识
        String subject = claims.getSubject();
        jwtAccount.setSubject(subject);
        //userId
        jwtAccount.setUserName(claims.get("userName", String.class));
        //签发者
        jwtAccount.setIssuer(claims.getIssuer());
        //签发时间
        jwtAccount.setIssuedAt(claims.getIssuedAt());
        //接收方
        jwtAccount.setAudience(claims.getAudience());
        //访问主张-角色
        jwtAccount.setNickName(claims.get("nickName", String.class));
        //访问主张-权限
        jwtAccount.setStatus(claims.get("status", Integer.class));
        return jwtAccount;
    }

    public static Map<String, Object> readValue(String val) {
        try {
            return MAPPER.readValue(val, Map.class);
        } catch (IOException e) {
            throw new MalformedJwtException("Unable to userpager JSON value: " + val, e);
        }
    }

    *//**
     * @Title auth
     * @Description token验证
     * @param @param trans
     * @param @return
     * @return Token
     *//*
//    public static Token auth(TransEntity<?> trans) {
//        Token token = null;
//        try {
//            token = JwtUtil.parseJwt(trans.getComment().getToken(), JwtUtil.SECRET_KEY);
//        } catch (NullPointerException e) {
//            throw new BaseException(Result.COMMON_AUTH_EMPTY_ERROR);
//        } catch (CompressionException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
//            throw new BaseException(Result.COMMON_AUTH_ERROR);
//        } catch (ExpiredJwtException e) {
//            throw new BaseException(Result.COMMON_AUTH_EXPIRE_ERROR);
//        } catch (SignatureException e) {
//            throw new BaseException(Result.COMMON_AUTH_SIGN_ERROR);
//        }
//
//        if (null == token) {
//            throw new BaseException(Result.COMMON_AUTH_ERROR);
//        }
//
//        return token;
//    }

    public static void main(String[] args) throws Exception{

        String jwt = JWTUtil.issueJWT( new UserVo(6,"admin","mm",-1));
        System.out.println("jwt:"+jwt);
        System.out.println("----------------------------------------");
//        Thread.sleep(1000);
        String ss = JWTUtil.parseJwtPayload(jwt);
        System.out.println("ss:"+ss);

        Token ooo = JWTUtil.parseJwt(jwt);
        System.out.println("ooo:"+ooo.getUserName());
    }
}*/
