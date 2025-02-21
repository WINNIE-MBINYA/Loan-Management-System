package com.loanmanagement.Service;

import com.loanmanagement.Dto.AuthRequest;
import com.loanmanagement.Dto.AuthResponse;
import com.loanmanagement.Dto.RegisterRequest;
import com.loanmanagement.Entity.User;
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
    private final PasswordEncoder passwordEncoder; // Change to PasswordEncoder
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()), request.getRole());
        userRepository.save(user);
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }
}
