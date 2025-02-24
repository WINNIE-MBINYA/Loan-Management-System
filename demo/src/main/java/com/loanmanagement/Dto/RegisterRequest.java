package com.loanmanagement.Dto;

import com.loanmanagement.Entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RegisterRequest {
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public RegisterRequest() {}

    public RegisterRequest(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
