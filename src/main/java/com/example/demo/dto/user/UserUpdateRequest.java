package com.example.demo.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequest (
        @NotBlank String username,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Pattern(regexp = UserCreateRequest.PASSWORD_POLICY, message = UserCreateRequest.PASSWORD_POLICY_MESSAGE) String password
) {
}
