package com.me.server.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.me.server.config.jwt.TokenProvider;
import com.me.server.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
  private final TokenProvider tokenProvider;
  private final RefreshTokenService refreshTokenService;
  private final UserService userService;

  public String generateToken(String refreshToken) {
    if (!tokenProvider.validToken(refreshToken)) {
      throw new IllegalArgumentException("Unexpected token");
    }

    Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
    User user = userService.findById(userId);

    return tokenProvider.generateToken(user, Duration.ofHours(2));
  }
}
