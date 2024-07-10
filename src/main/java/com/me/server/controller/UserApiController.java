package com.me.server.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.me.server.domain.User;
import com.me.server.dto.AddUserRequest;
import com.me.server.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
public class UserApiController {
  private final UserService userService;

  @PostMapping("/sign-up")
  public ResponseEntity<User> signUp(@RequestBody AddUserRequest request) {

    User savedUser = userService.save(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
  }

  @GetMapping("/user")
  public List<User> findAll() {
    return userService.findAll();
  }

  // @GetMapping("/logout")
  // public ResponseEntity<User> logout()

}
