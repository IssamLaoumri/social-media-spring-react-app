package com.laoumri.springbootbackend.controllers;

import com.laoumri.springbootbackend.dto.requests.ReactRequest;
import com.laoumri.springbootbackend.services.ReactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reacts")
@RequiredArgsConstructor
public class ReactController {
    private final ReactService reactService;

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> reactToPost(@PathVariable int id, @Valid @RequestBody ReactRequest reactRequest){
        reactService.reactToPost(id, reactRequest.getReactType());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
