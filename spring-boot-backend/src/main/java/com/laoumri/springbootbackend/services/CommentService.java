package com.laoumri.springbootbackend.services;

import com.laoumri.springbootbackend.dto.requests.CreateCommentRequest;
import com.laoumri.springbootbackend.dto.responses.MessageResponse;

public interface CommentService {
    MessageResponse addComment(int postId, CreateCommentRequest request);
    MessageResponse deleteComment(int commentId);
    MessageResponse updateComment(int commentId, CreateCommentRequest request);
}
