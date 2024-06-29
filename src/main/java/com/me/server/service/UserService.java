package com.me.server.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.me.server.dto.AddUserRequest;
import com.me.server.model.User;
import com.me.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

  @Autowired
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public User save(AddUserRequest dto) {
    return userRepository.save(User.builder()
        .email(dto.getEmail())
        .password(bCryptPasswordEncoder.encode(dto.getPassword()))
        .createdAt(LocalDateTime.now())
        .build());
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }
}
