package com.laoumri.springbootbackend.servicesImpl;

import com.laoumri.springbootbackend.Exceptions.InvalidRequestException;
import com.laoumri.springbootbackend.Exceptions.ResourceNotFoundException;
import com.laoumri.springbootbackend.dto.responses.MessageResponse;
import com.laoumri.springbootbackend.entities.FriendRequest;
import com.laoumri.springbootbackend.entities.User;
import com.laoumri.springbootbackend.enums.EMessage;
import com.laoumri.springbootbackend.enums.FriendRequestStatus;
import com.laoumri.springbootbackend.repositories.FriendRequestRepository;
import com.laoumri.springbootbackend.repositories.UserRepository;
import com.laoumri.springbootbackend.services.UserRelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRelationshipServiceImpl implements UserRelationshipService {
    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;

    @Override
    public MessageResponse addFriend(int receiverId) {
        User currentUser = getCurrentUser();
        User receiver = getParamUser(receiverId, currentUser);

        // Check if the request is already sent or the requested and requester are already friends
        Optional<FriendRequest> existingRequest = friendRequestRepository.findByRequesterAndRequested(currentUser, receiver);

        boolean isAlreadyFriends = receiver.getFriends().contains(currentUser);

        if (existingRequest.isPresent())
            throw new InvalidRequestException(EMessage.FRIEND_REQUEST_ALREADY_SENT.name());
        if(isAlreadyFriends)
            throw new InvalidRequestException(EMessage.ALREADY_FRIENDS.name());

        // If no exception is thrown we well create and save the friend request
        FriendRequest friendRequest = FriendRequest.builder()
                .requested(receiver)
                .requester(currentUser)
                .requestedAt(Instant.now())
                .status(FriendRequestStatus.PENDING)
                .build();

        friendRequestRepository.save(friendRequest);

        return new MessageResponse(EMessage.FRIEND_REQUEST_SENT.name());
    }

    @Override
    public MessageResponse cancelFriendRequest(int requestId) {
        Optional<FriendRequest> request = friendRequestRepository.findById(requestId);
        User requester = request.get().getRequester();
        User requested = request.get().getRequested();

        User currentUser = getCurrentUser();
        if(!currentUser.equals(requester) && !currentUser.equals(requested))
            throw new InvalidRequestException(EMessage.REQUEST_NOT_ALLOWED.name());


        boolean isAlreadyFriends = requester.getFriends().contains(requested) &&
                requested.getFriends().contains(requester);

        if(request.isEmpty())
            throw new ResourceNotFoundException(EMessage.FRIEND_REQUEST_NOT_FOUND.name());
        if (isAlreadyFriends)
            throw new InvalidRequestException(EMessage.ALREADY_FRIENDS.name());

        friendRequestRepository.delete(request.get());

        return new MessageResponse(EMessage.FRIEND_REQUEST_CANCELED.name());
    }

    @Override
    public MessageResponse acceptFriendRequest(int senderId) {
        User currentUser = getCurrentUser();
        User sender = getParamUser(senderId, currentUser);

        Optional<FriendRequest> request = friendRequestRepository.findByRequesterAndRequested(sender, currentUser);
        boolean isAlreadyFriends = currentUser.getFriends().contains(sender) && sender.getFriends().contains(currentUser);

        if(request.isEmpty() || request.get().getStatus() != FriendRequestStatus.PENDING)
            throw new ResourceNotFoundException(EMessage.FRIEND_REQUEST_NOT_FOUND.name());
        if(isAlreadyFriends)
            throw new InvalidRequestException(EMessage.ALREADY_FRIENDS.name());

        currentUser.getFriends().add(sender);
        sender.getFriends().add(currentUser);
        userRepository.saveAll(Arrays.asList(sender, currentUser));

        request.get().setStatus(FriendRequestStatus.ACCEPTED);
        friendRequestRepository.save(request.get());

        return new MessageResponse(EMessage.FRIEND_REQUEST_ACCEPTED.name());
    }

    @Override
    public MessageResponse unfriend(int senderId) {
        User currentUser = getCurrentUser();
        User friend = getParamUser(senderId, currentUser);
        if(currentUser.getFriends().contains(friend) && friend.getFriends().contains(currentUser)){
            currentUser.getFriends().remove(friend);
            friend.getFriends().remove(currentUser);
            userRepository.saveAll(Arrays.asList(currentUser, friend));
            return new MessageResponse(EMessage.UNFRIEND_SUCCESSFUL.name());
        }
        throw new ResourceNotFoundException(EMessage.NOT_FRIENDS.name());
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(EMessage.USER_NOT_FOUND.name()));
    }

    private User getParamUser(int id, User currentUser) {
        if (Objects.equals(currentUser.getId(), id)) {
            throw new InvalidRequestException(EMessage.REQUEST_NOT_ALLOWED.name());
        }
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(EMessage.USER_NOT_FOUND.name()));
    }

}
