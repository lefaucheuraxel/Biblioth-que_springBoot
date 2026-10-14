package com.example.demo.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDeleteRequest(

        @NotBlank String username)    {
}
