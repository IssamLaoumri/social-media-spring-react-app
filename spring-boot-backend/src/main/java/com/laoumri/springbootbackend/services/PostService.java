package com.laoumri.springbootbackend.services;

import com.laoumri.springbootbackend.dto.requests.CreatePostRequest;
import com.laoumri.springbootbackend.dto.responses.MessageResponse;

public interface PostService {
    MessageResponse createPost(CreatePostRequest request);
}
