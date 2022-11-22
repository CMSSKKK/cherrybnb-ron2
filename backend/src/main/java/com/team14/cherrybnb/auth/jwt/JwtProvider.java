package com.team14.cherrybnb.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class JwtProvider {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String id) {
        LocalDateTime now = LocalDateTime.now();
        return Jwts.builder()
                .setAudience(id)
                .setIssuer("strangeriako")
                .setSubject("github")
                .setIssuedAt(Date.from(now.toInstant(ZoneOffset.UTC)))
                .setExpiration(Date.from(now.plusMinutes(30L).toInstant(ZoneOffset.UTC)))
                .signWith(secretKey)
                .compact();
    }

    public String verifyToken(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getAudience();
    }

}
