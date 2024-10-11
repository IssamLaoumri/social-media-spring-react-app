package com.laoumri.springbootbackend.dto.responses;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private final String tokenType = "Bearer ";
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private Set<String> roles;
}
