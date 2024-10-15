package com.laoumri.springbootbackend.servicesImpl;

import com.laoumri.springbootbackend.Exceptions.ResourceNotFoundException;
import com.laoumri.springbootbackend.dto.responses.ProfileResponse;
import com.laoumri.springbootbackend.entities.FriendRequest;
import com.laoumri.springbootbackend.entities.User;
import com.laoumri.springbootbackend.enums.FriendRequestStatus;
import com.laoumri.springbootbackend.models.MediaModel;
import com.laoumri.springbootbackend.models.PostModel;
import com.laoumri.springbootbackend.models.RelationshipModel;
import com.laoumri.springbootbackend.models.FriendModel;
import com.laoumri.springbootbackend.repositories.FriendRequestRepository;
import com.laoumri.springbootbackend.repositories.UserRepository;
import com.laoumri.springbootbackend.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    @Override
    public ProfileResponse getProfile(String username) {
        // Get the requested profile user
        User profile = userRepository.findBy_username(username).orElseThrow(()-> new ResourceNotFoundException("This profile is no longer exists"));

        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found."));

        // Initialise the state of the relationship {request sent, add friend, accept request, friends}
        RelationshipModel relationship = new RelationshipModel();
        System.out.println(user.getFriends().contains(profile) && profile.getFriends().contains(user));
        if(user.getFriends().contains(profile) && profile.getFriends().contains(user))
            relationship.setAreFriends(true);

        // Check if the user has received a friend request from the profile user
        Optional<FriendRequest> receivedRequest = friendRequestRepository.findByRequesterAndRequestedAndStatus(profile, user, FriendRequestStatus.PENDING);
        if (receivedRequest.isPresent()) {
            relationship.setFriendRequestReceived(true);
        }

        // Check if the user has sent a friend request to the profile user
        Optional<FriendRequest> sentRequest = friendRequestRepository.findByRequesterAndRequestedAndStatus(user, profile,FriendRequestStatus.PENDING);
        if (sentRequest.isPresent()) {
            relationship.setFriendRequestSent(true);
        }

        // Map only necessary infos of each friend of the profile user
        Set<FriendModel> friends = profile.getFriends().stream().map((friend) -> FriendModel.builder()
                .username(friend.get_username())
                .lastname(friend.getLastname())
                .firstname(friend.getFirstname())
                .build()).collect(Collectors.toSet());


        // Map posts
        List<PostModel> posts = profile.getPosts().stream().map((post) -> PostModel.builder()
                .type(post.getType().name())
                .id(post.getId())
                .content(post.getContent())
                .publishedAt(post.getPublishedAt())
                .media(post.getMedia().stream().map((media)-> MediaModel.builder()
                            .url(media.getUrl())
                            .id(media.getId())
                            .type(media.getType().name())
                            .build()
                ).toList())
                .build()
        ).toList();

        // Return a profile response
        return ProfileResponse.builder()
                .posts(posts)
                .firstname(profile.getFirstname())
                .lastname(profile.getLastname())
                .friends(friends)
                .isAccountDisabled(profile.isDisabled())
                .relationship(relationship)
                .username(username)
                .build();
    }
}
