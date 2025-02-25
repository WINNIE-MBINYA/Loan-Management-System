package com.loanmanagement.Service;

import com.loanmanagement.Dto.AuthRequest;
import com.loanmanagement.Dto.RegisterRequest;
import com.loanmanagement.Entity.User;
import com.loanmanagement.Exception.UserNotFoundException;
import com.loanmanagement.Repository.UserRepository;
import com.loanmanagement.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {
        // Encode password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Log registration attempt
        System.out.println("Registering user: " + request.getUsername() + ", Encoded password: " + encodedPassword);

        // Create and save user
        User user = new User(request.getUsername(), encodedPassword, request.getRole());
        userRepository.save(user);

        return "Registration successful";
    }

    public String authenticate(AuthRequest request) {
        try {
            // Log authentication attempt
            System.out.println("Authenticating user: " + request.getUsername());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

        } catch (Exception e) {
            // Log authentication failure
            System.err.println("Authentication failed for username: " + request.getUsername());
            throw e; // Rethrow the exception for further handling
        }

        // Retrieve the user from the database
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User " + request.getUsername() + " not found!"));

        // Generate JWT Token for the authenticated user
        return jwtUtil.generateToken(user);
    }
}
