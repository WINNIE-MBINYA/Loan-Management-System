package com.loanmanagement.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AuthResponse {

    // Manually add constructor
    public AuthResponse(String token) {
    }
}
