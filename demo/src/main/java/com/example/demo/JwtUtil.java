package com.example.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // 1. 加密密钥（实际项目中放在配置文件里，这里固定是为了方便）
    private static final String SECRET = "your-256-bit-secret-your-256-bit-secret"; // 至少32位
    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 2. 令牌有效期（7天，单位毫秒）
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    // 3. 生成令牌（传入用户ID和用户名）
    public static String generateToken(Long userId, String username) {
        return Jwts.builder()
                .setSubject(username)               // 用户名（主体）
                .claim("userId", userId)            // 自定义字段：用户ID
                .setIssuedAt(new Date())            // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // 过期时间
                .signWith(key, SignatureAlgorithm.HS256) // 签名加密
                .compact();
    }

    // 4. 解析令牌（验证合法性，并取出里面的数据）
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 5. 验证令牌是否有效（是否被篡改或过期）
    public static boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
