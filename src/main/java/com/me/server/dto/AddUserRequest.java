package com.me.server.dto;

import com.me.server.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {

  private String email;
  private String password;

  public User toEntity() {
    return User.builder()
        .email(email)
        .password(password)
        .build();
  }
}
