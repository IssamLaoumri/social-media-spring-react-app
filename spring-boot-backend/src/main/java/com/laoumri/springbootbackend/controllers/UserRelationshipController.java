package com.laoumri.springbootbackend.controllers;

import com.laoumri.springbootbackend.dto.responses.MessageResponse;
import com.laoumri.springbootbackend.services.UserRelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class UserRelationshipController {
    private final UserRelationshipService userRelationshipService;

    @PostMapping("/send/{receiverId}")
    public ResponseEntity<MessageResponse> addFriend(@PathVariable int receiverId){
        return ResponseEntity.status(HttpStatus.OK).body(userRelationshipService.addFriend(receiverId));
    }

    @DeleteMapping("/delete/{requestId}")
    public ResponseEntity<MessageResponse> cancelFriendRequest(@PathVariable int requestId){
        return ResponseEntity.status(HttpStatus.OK).body(userRelationshipService.cancelFriendRequest(requestId));
    }

    @PostMapping("/accept/{requestId}")
    public ResponseEntity<MessageResponse> acceptFriendRequest(@PathVariable int requestId){
        return ResponseEntity.status(HttpStatus.OK).body(userRelationshipService.acceptFriendRequest(requestId));
    }

    @DeleteMapping("/unfriend/{senderId}")
    public ResponseEntity<MessageResponse> unfriend(@PathVariable int senderId){
        return ResponseEntity.status(HttpStatus.OK).body(userRelationshipService.unfriend(senderId));
    }
}
