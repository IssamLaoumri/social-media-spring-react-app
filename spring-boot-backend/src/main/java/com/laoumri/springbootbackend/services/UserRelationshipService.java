package com.laoumri.springbootbackend.services;

import com.laoumri.springbootbackend.dto.requests.FriendRequest;
import com.laoumri.springbootbackend.dto.responses.MessageResponse;

public interface UserRelationshipService {
    MessageResponse addFriend(FriendRequest request);
    MessageResponse cancelFriendRequest(FriendRequest request);
}
