package com.laoumri.springbootbackend.services;

import com.laoumri.springbootbackend.dto.responses.MessageResponse;

public interface UserRelationshipService {
    MessageResponse addFriend(int receiverId);
    MessageResponse senderCancelFriendRequest(int receiverId);
    MessageResponse acceptFriendRequest(int senderId);
    MessageResponse unfriend(int senderId);
    MessageResponse receiverDeleteFriendRequest(int senderId);
}
