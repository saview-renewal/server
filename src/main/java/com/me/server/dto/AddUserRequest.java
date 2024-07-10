package com.me.server.dto;

import com.me.server.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {

  private String email;
  private String nickname;
  private String provider;

  public User toEntity() {
    return User.builder()
        .email(email)
        .nickname(nickname)
        .provider(provider)
        .build();
  }
}
