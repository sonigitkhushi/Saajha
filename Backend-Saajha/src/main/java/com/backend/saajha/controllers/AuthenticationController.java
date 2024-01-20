package com.backend.saajha.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.backend.saajha.dtos.UserDto;
import com.backend.saajha.dtos.UserLoginDto;
import com.backend.saajha.exceptions.AppException;
import com.backend.saajha.models.User;
import com.backend.saajha.services.AuthenticationService;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public User register( @RequestBody UserDto user) {
        if (user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new AppException("All fields are required.", HttpStatus.BAD_REQUEST);
        }

        return authenticationService.register(user.getEmail(), user.getPassword());
    }

    @PostMapping("/login")
    public UserLoginDto login(@Valid @RequestBody UserDto user) {
        UserLoginDto userLoginDto = authenticationService.login(user.getEmail(), user.getPassword());

        if (userLoginDto.getUser() == null) {
            throw new AppException("Invalid credentials.", HttpStatus.NOT_FOUND);
        }

        return userLoginDto;
    }
}
