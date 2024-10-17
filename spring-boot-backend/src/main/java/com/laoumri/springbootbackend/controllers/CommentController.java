package com.laoumri.springbootbackend.controllers;

import com.laoumri.springbootbackend.dto.requests.CreateCommentRequest;
import com.laoumri.springbootbackend.dto.responses.MessageResponse;
import com.laoumri.springbootbackend.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<MessageResponse> createComment(@PathVariable int postId,@RequestBody CreateCommentRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.addComment(postId, request));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable int commentId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(commentId));
    }
}
