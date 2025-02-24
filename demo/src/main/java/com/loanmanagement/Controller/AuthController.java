package com.loanmanagement.Controller;

import com.loanmanagement.Dto.AuthRequest;
import com.loanmanagement.Dto.AuthResponse;
import com.loanmanagement.Dto.RegisterRequest;
import com.loanmanagement.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        String resp = authenticationService.register(request);
        System.out.println(resp);
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        System.out.println(request);
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
