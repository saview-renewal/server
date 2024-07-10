package com.me.server.config.jwt;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.me.server.domain.User;
import com.me.server.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import io.jsonwebtoken.Jwts;

@SpringBootTest
public class TokenProviderTest {
  @Autowired
  private TokenProvider tokenProvider;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JwtProperties jwtProperties;

  @DisplayName("토큰 생성")
  @Test
  public void createToken() {
    User testUser = userRepository.save(User.builder()
        .email("test@gmail.com")
        .password("test")
        .build());

    String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

    Long userId = Jwts.parser()
        .setSigningKey(jwtProperties.getSecretKey())
        .parseClaimsJws(token)
        .getBody()
        .get("id", Long.class);

    assertThat(userId).isEqualTo(testUser.getId());
  }

  @DisplayName("토큰 유효성 검사 : 만료")
  @Test
  void validToken_invalidToken() {
    String token = JwtFactory.builder().expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
        .build().createToken(jwtProperties);

    boolean result = tokenProvider.validToken(token);
    assertThat(result).isFalse();
  }

  @DisplayName("토큰 유효성 검사 : 유효")
  @Test
  void validToken_validToken() {
    String token = JwtFactory.builder().build().createToken(jwtProperties);
    boolean result = tokenProvider.validToken(token);
    assertThat(result).isTrue();
  }

  @DisplayName("Authentication 가져오기")
  @Test
  void getAuthentication() {
    String userEmail = "test@gmail.com";
    String token = JwtFactory.builder().subject(userEmail).build().createToken(jwtProperties);
    Authentication authentication = tokenProvider.getAuthentication(token);
    assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
  }

  @DisplayName("UserId 가져오기")
  @Test
  void getUserId() {
    Long userId = 1L;
    String token = JwtFactory.builder().claims(Map.of("id", userId)).build().createToken(jwtProperties);
    Long userIdByToken = tokenProvider.getUserId(token);
    assertThat(userIdByToken).isEqualTo(userId);
  }
}
