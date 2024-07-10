package com.me.server.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.me.server.domain.User;
import com.me.server.dto.AddUserRequest;
import com.me.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

  @Autowired
  private final UserRepository userRepository;

  public User save(AddUserRequest dto) {
    return userRepository.save(User.builder()
        .email(dto.getEmail())
        .nickname(dto.getNickname())
        .provider(dto.getProvider())
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build());
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User findById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
  }
}
