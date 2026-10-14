package com.example.demo.mapper;

import com.example.demo.dto.user.UserCreateRequest;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.dto.user.UserUpdateRequest;
import com.example.demo.entity.UserEntity;

public class UserMapper {

    private UserMapper() {}

    public static UserEntity toEntity(UserCreateRequest request) {
        return UserEntity.builder()
                .username(request.username())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();
    }

    public static UserResponse toResponse(UserEntity user) {
        return new UserResponse(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public static void applyUpdate(UserEntity target, UserUpdateRequest request) {
        if (request.username() != null) {
            target.setUsername(request.username());
        }
        if (request.firstName() != null) {
            target.setFirstName(request.firstName());
        }
        if (request.lastName() != null) {
            target.setLastName(request.lastName());
        }
    }

}
