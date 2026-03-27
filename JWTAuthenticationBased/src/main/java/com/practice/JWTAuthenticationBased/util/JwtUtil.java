package com.practice.JWTAuthenticationBased.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtUtil {
    private final String SECRET_KEY="79LBhZS1YKW+HT8D9+c9lNhd+DqU0OJ9PxWlAa0BvLg=";
    SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }

    public Claims extractClaims(String token){
        System.out.println("Extracting claims starts ...");
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token){
        System.out.println("Extracting name from token starts...");
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        System.out.println("Validating token starts...");
        Claims claims = extractClaims(token);
        System.out.println("Name: "+claims.getSubject());
        System.out.println("Issued At: "+claims.getIssuedAt());
        System.out.println("Expiration At: "+claims.getExpiration());
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token)
    {
        System.out.println("Checking token Expiration...");
        Date expriration = extractClaims(token).getExpiration();
        return expriration.before(new Date());
    }
}
