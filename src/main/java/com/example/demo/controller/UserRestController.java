package com.example.demo.controller;


import com.example.demo.dto.user.UserCreateRequest;
import com.example.demo.dto.user.UserDeleteRequest;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.dto.user.UserUpdateRequest;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserResponse getUserById(@RequestParam Long id) {
        return userService.getUserById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserCreateRequest request) {
        return userService.saveUser(request);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse deleteUser(@PathVariable Long id, @Valid @RequestBody UserDeleteRequest request) {
        return userService.deleteUser(id, request);
    }

}
