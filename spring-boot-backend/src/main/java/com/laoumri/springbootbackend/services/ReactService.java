package com.laoumri.springbootbackend.services;

import com.laoumri.springbootbackend.dto.requests.ReactRequest;

public interface ReactService {
    void reactToPost(int postId, String reactType);
    void deleteReact(int reactId);
    void updateReact(int reactId, ReactRequest request);
}
