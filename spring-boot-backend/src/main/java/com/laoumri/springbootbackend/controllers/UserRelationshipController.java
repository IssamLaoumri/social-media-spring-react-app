package com.laoumri.springbootbackend.controllers;

import com.laoumri.springbootbackend.dto.requests.FriendRequest;
import com.laoumri.springbootbackend.dto.responses.MessageResponse;
import com.laoumri.springbootbackend.services.UserRelationshipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class UserRelationshipController {
    private final UserRelationshipService userRelationshipService;

    @PostMapping
    public ResponseEntity<MessageResponse> addFriend(@Valid @RequestBody FriendRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(userRelationshipService.addFriend(request));
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> cancelFriendRequest(@Valid @RequestBody FriendRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(userRelationshipService.cancelFriendRequest(request));
    }
}
