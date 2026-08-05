package com.example.Sharyan.security;

import com.example.Sharyan.entity.RefreshToken;
import com.example.Sharyan.entity.User;
import com.example.Sharyan.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;


    public RefreshToken saveRefreshToken(User user , String token){

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(token);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(refreshExpiration / 1000));

        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByToken(String token){

        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }

    public boolean isValid(RefreshToken refreshToken){

        return !refreshToken.isRevoked()
                &&
                refreshToken.getExpiryDate()
                        .isAfter(LocalDateTime.now());
    }



}
