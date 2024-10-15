package com.laoumri.springbootbackend.controllers;

import com.laoumri.springbootbackend.dto.responses.ProfileResponse;
import com.laoumri.springbootbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getProfile(username));
    }
}
