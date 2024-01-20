package com.backend.saajha.services;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.saajha.dtos.UserLoginDto;
import com.backend.saajha.models.User;
import com.backend.saajha.models.UserRole;
import com.backend.saajha.repository.UserRepository;
import com.backend.saajha.repository.UserRoleRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public User register(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        UserRole userRole = userRoleRepository.findByAuthority("USER")
                .orElseGet(() -> createDefaultUserRole()); // Create if not found

        Set<UserRole> authorities = new HashSet<>();
        authorities.add(userRole);

        return userRepository.save(new User(email, encodedPassword, authorities));
    }

    private UserRole createDefaultUserRole() {
        return userRoleRepository.save(new UserRole("USER"));
    }

    public UserLoginDto login(String email, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String token = tokenService.generateJwt(auth);

            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                return new UserLoginDto(user.getId(), user, token);
            } else {
                return new UserLoginDto(null, null, "");
            }

        } catch (AuthenticationException e) {
            return new UserLoginDto(null, null, "");
        }
    }
}
