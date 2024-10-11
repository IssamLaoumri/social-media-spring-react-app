package com.laoumri.springbootbackend.services;

import com.laoumri.springbootbackend.dto.requests.LoginRequest;
import com.laoumri.springbootbackend.dto.requests.RegisterRequest;
import com.laoumri.springbootbackend.dto.responses.AuthResponse;

public interface AuthService {
    String register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
}
