package com.laoumri.springbootbackend.services;

import com.laoumri.springbootbackend.dto.responses.MessageResponse;

public interface UserRelationshipService {
    MessageResponse addFriend(int receiverId);
    MessageResponse cancelFriendRequest(int requestId);
    MessageResponse acceptFriendRequest(int requestId);
    MessageResponse unfriend(int senderId);
}
