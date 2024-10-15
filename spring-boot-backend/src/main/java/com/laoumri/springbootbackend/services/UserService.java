package com.laoumri.springbootbackend.services;

import com.laoumri.springbootbackend.dto.responses.ProfileResponse;

public interface UserService {
    ProfileResponse getProfile(String username);
}
