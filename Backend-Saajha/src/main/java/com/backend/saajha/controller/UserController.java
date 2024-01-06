package com.backend.saajha.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.backend.saajha.dto.UserDto;
import com.backend.saajha.model.User;
import com.backend.saajha.service.UserService;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId, Authentication authentication) {
        return userService.getUserById(userId, authentication);
    }

    @PutMapping("/update/{userId}")
    public User updateUserById(@PathVariable Long userId, @RequestBody UserDto userDto, Authentication authentication) {
        return userService.updateUserById(userId, userDto, authentication);
    }
}
