package com.example.demo.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequest(
        @NotBlank String username,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank @Pattern(regexp = UserCreateRequest.PASSWORD_POLICY, message = UserCreateRequest.PASSWORD_POLICY_MESSAGE) String password
){
    public static final String PASSWORD_POLICY = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{12,}$";
    public static final String PASSWORD_POLICY_MESSAGE = "must be at least 12 characters and contain an uppercase letter, a lowercase letter, a digit and a special character";
}
