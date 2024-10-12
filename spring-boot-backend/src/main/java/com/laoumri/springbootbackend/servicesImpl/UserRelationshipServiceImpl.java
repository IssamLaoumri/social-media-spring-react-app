package com.laoumri.springbootbackend.servicesImpl;

import com.laoumri.springbootbackend.Exceptions.InvalidRequestException;
import com.laoumri.springbootbackend.Exceptions.ResourceNotFoundException;
import com.laoumri.springbootbackend.dto.requests.FriendRequest;
import com.laoumri.springbootbackend.dto.responses.MessageResponse;
import com.laoumri.springbootbackend.entities.User;
import com.laoumri.springbootbackend.repositories.UserRepository;
import com.laoumri.springbootbackend.services.UserRelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserRelationshipServiceImpl implements UserRelationshipService {
    private final UserRepository userRepository;

    @Override
    public MessageResponse addFriend(FriendRequest request) {
        User currentUser = getCurrentUser();
        User receiver = getReceiverUser(request.getReceiverId(), currentUser);

        if (!receiver.getRequests().contains(currentUser) && !receiver.getFriends().contains(currentUser)) {
            receiver.getRequests().add(currentUser);
            userRepository.save(receiver);
            return new MessageResponse("Friend request has been sent.");
        }
        return new MessageResponse("Friend request has already been sent.");
    }

    @Override
    public MessageResponse cancelFriendRequest(FriendRequest request) {
        User currentUser = getCurrentUser();
        User receiver = getReceiverUser(request.getReceiverId(), currentUser);

        if (receiver.getRequests().contains(currentUser)) {
            receiver.getRequests().remove(currentUser);
            userRepository.save(receiver);
            return new MessageResponse("Friend request has been canceled.");
        }
        throw new ResourceNotFoundException("Friend request not found.");
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private User getReceiverUser(int receiverId, User currentUser) {
        if (Objects.equals(currentUser.getId(), receiverId)) {
            throw new InvalidRequestException("Request not allowed.");
        }
        return userRepository.findById(receiverId).orElseThrow(() -> new ResourceNotFoundException("This user no longer exists."));
    }

}
