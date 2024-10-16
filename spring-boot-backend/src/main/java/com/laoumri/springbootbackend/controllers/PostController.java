package com.laoumri.springbootbackend.controllers;

import com.laoumri.springbootbackend.dto.requests.CreatePostRequest;
import com.laoumri.springbootbackend.dto.responses.MessageResponse;
import com.laoumri.springbootbackend.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<MessageResponse> createPost(@Valid @RequestBody CreatePostRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.deletePost(id));
    }
}
