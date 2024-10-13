package com.laoumri.springbootbackend.servicesImpl;

import com.laoumri.springbootbackend.Exceptions.InvalidRequestException;
import com.laoumri.springbootbackend.Exceptions.ResourceNotFoundException;
import com.laoumri.springbootbackend.dto.responses.MessageResponse;
import com.laoumri.springbootbackend.entities.User;
import com.laoumri.springbootbackend.repositories.UserRepository;
import com.laoumri.springbootbackend.services.UserRelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserRelationshipServiceImpl implements UserRelationshipService {
    private final UserRepository userRepository;

    @Override
    public MessageResponse addFriend(int receiverId) {
        User currentUser = getCurrentUser();
        User receiver = getParamUser(receiverId, currentUser);
        boolean isRequestAlreadyBeenSent = receiver.getRequests().contains(currentUser);
        boolean isAlreadyFriends = receiver.getFriends().contains(currentUser);
        if (!isRequestAlreadyBeenSent && !isAlreadyFriends) {
            receiver.getRequests().add(currentUser);
            userRepository.save(receiver);
            return new MessageResponse("Friend request has been sent.");
        }
        return isRequestAlreadyBeenSent ?
                new MessageResponse("Friend request has already been sent.") :
                new MessageResponse("You are already friends.");
    }

    @Override
    public MessageResponse senderCancelFriendRequest(int receiverId) {
        User currentUser = getCurrentUser();
        User receiver = getParamUser(receiverId, currentUser);
        boolean isRequestAlreadyBeenSent = receiver.getRequests().contains(currentUser);
        boolean isAlreadyFriends = receiver.getFriends().contains(currentUser);
        if(isAlreadyFriends){
            throw new InvalidRequestException("You are already friends.");
        }
        if(!isRequestAlreadyBeenSent){
            throw new ResourceNotFoundException("Friend request not found.");
        }
        receiver.getRequests().remove(currentUser);
        userRepository.save(receiver);
        return new MessageResponse("Friend request has been canceled.");
    }

    @Override
    public MessageResponse acceptFriendRequest(int senderId) {
        User receiver = getCurrentUser();
        User sender = getParamUser(senderId, receiver);
        if (receiver.getRequests().contains(sender)){
            receiver.getFriends().add(sender);
            sender.getFriends().add(receiver);
            receiver.getRequests().remove(sender);
            userRepository.saveAll(Arrays.asList(receiver, sender));
            return new MessageResponse("Friend request has been accepted.");
        }
        if(receiver.getFriends().contains(sender)){
            throw new InvalidRequestException("You are already friends.");
        }
        throw new ResourceNotFoundException("Friend request not found.");
    }

    @Override
    public MessageResponse unfriend(int senderId) {
        User currentUser = getCurrentUser();
        User sender = getParamUser(senderId, currentUser);
        if(currentUser.getFriends().contains(sender) && sender.getFriends().contains(currentUser)){
            currentUser.getFriends().remove(sender);
            sender.getFriends().remove(currentUser);
            userRepository.saveAll(Arrays.asList(currentUser, sender));
            return new MessageResponse("Unfriend request accepted.");
        }
        throw new ResourceNotFoundException("You are not friends.");
    }

    @Override
    public MessageResponse receiverDeleteFriendRequest(int senderId) {
        User receiver = getCurrentUser();
        User sender = getParamUser(senderId, receiver);
        if(receiver.getRequests().contains(sender)){
            receiver.getRequests().remove(sender);
            userRepository.save(receiver);
            return new MessageResponse("Friend Request has been deleted.");
        }
        if(receiver.getFriends().contains(sender) && sender.getFriends().contains(receiver)){
            throw new InvalidRequestException("You are already friends.");
        }
        throw new ResourceNotFoundException("Friend request not found.");
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private User getParamUser(int id, User currentUser) {
        if (Objects.equals(currentUser.getId(), id)) {
            throw new InvalidRequestException("Request not allowed.");
        }
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("This user no longer exists."));
    }

}
