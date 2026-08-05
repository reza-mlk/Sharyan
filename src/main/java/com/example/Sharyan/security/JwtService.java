package com.example.Sharyan.security;

import com.example.Sharyan.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Service
public class JwtService {


    @Value("${jwt.secret}")
    private String secret;


    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

//    ================= ACCESS TOKEN =======================
    public String generateToken(User user){

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("type" , "ACCESS")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration)
                )
                .signWith(getKey())
                .compact();
    }

    //    ==================== REFRESH TOKEN ====================================
    public String generateRefreshToken(User user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("type" , "REFRESH")
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + refreshExpiration)
                )
                .signWith(getKey())
                .compact();

    }

    public String extractUsername(String token){

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String extractTokenType(String token){

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("type" , String.class);
    }



    public boolean isValid(String token , UserDetails userDetails){
        String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    public boolean isExpired(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }
    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

}
