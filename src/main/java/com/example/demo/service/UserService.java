package com.example.demo.service;

import com.example.demo.dto.user.UserCreateRequest;
import com.example.demo.dto.user.UserDeleteRequest;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.dto.user.UserUpdateRequest;
import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoanService loanService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, LoanService loanService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loanService = loanService;
    }

    public UserResponse findByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User with username " + username + " not found.");
        }
        return UserMapper.toResponse(user);
    }
    public UserResponse getUserById(Long id) {
        if(userRepository.findById(id).isEmpty()){
            throw new RuntimeException("User with id " + id + " not found.");
        }
        return UserMapper.toResponse(userRepository.findById(id).get());
    }

    @Transactional
    public UserResponse saveUser(UserCreateRequest user) {
        if (userRepository.findByUsername(user.username()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        log.info("Saving user with username {}", user.username());
        UserEntity entity = UserMapper.toEntity(user);
        entity.setPassword(passwordEncoder.encode(user.password()));
        return UserMapper.toResponse(userRepository.save(entity));
    }

    @Transactional
    public UserResponse updateUser(Long id, UserUpdateRequest user) {
        UserEntity existing = userRepository.findById(id).orElse(null);
        if (existing == null) {
            throw new RuntimeException("User with id " + id + " not found.");
        }
        log.info("Updating user with id {}", id);
        UserMapper.applyUpdate(existing, user);
        if (user.password() != null) {
            existing.setPassword(passwordEncoder.encode(user.password()));
        }
        return UserMapper.toResponse(userRepository.save(existing));
    }

    @Transactional
    public UserResponse deleteUser(Long id, UserDeleteRequest request) {
        Optional<UserEntity> existing = userRepository.findById(id);
        if (existing.isEmpty()) {
            throw new RuntimeException("User with id " + id + " not found.");
        }
        if(!existing.get().getUsername().equals(request.username())) {
            throw new RuntimeException("Username does not match for user with id " + id);
        }
        log.info("Deleting user with id {} and username {}", id, request.username());

        UserEntity user = existing.get();
        loanService.deleteLoansOfUser(user.getId());
        userRepository.delete(user);

        return UserMapper.toResponse(user);
    }


}
