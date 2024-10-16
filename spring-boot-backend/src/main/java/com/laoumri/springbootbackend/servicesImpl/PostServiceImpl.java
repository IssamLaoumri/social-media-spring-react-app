package com.laoumri.springbootbackend.servicesImpl;

import com.laoumri.springbootbackend.Exceptions.AccessDeniedException;
import com.laoumri.springbootbackend.Exceptions.InvalidEnumException;
import com.laoumri.springbootbackend.Exceptions.InvalidRequestException;
import com.laoumri.springbootbackend.dto.requests.CreatePostRequest;
import com.laoumri.springbootbackend.dto.requests.MediaRequest;
import com.laoumri.springbootbackend.dto.responses.MessageResponse;
import com.laoumri.springbootbackend.entities.Media;
import com.laoumri.springbootbackend.entities.Post;
import com.laoumri.springbootbackend.entities.User;
import com.laoumri.springbootbackend.enums.EMedia;
import com.laoumri.springbootbackend.enums.EPost;
import com.laoumri.springbootbackend.repositories.PostRepository;
import com.laoumri.springbootbackend.repositories.UserRepository;
import com.laoumri.springbootbackend.services.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    @Override
    public MessageResponse createPost(CreatePostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        EPost postType;
        try {
            postType = EPost.valueOf(request.getType());
        } catch (IllegalArgumentException ex) {
            throw new InvalidEnumException("Invalid post type: " + request.getType());
        }

        validateProfileOrCoverPicture(request);

        Post post = Post.builder()
                .content(request.getContent())
                .user(user)
                .type(postType)
                .publishedAt(Instant.now())
                .build();

        List<Media> medias = request.getMedias()!=null ? request.getMedias().stream()
                .map(mediaRequest -> {
                    EMedia mediaType;
                    try {
                        mediaType = EMedia.valueOf(mediaRequest.getType());
                    } catch (IllegalArgumentException ex) {
                        throw new InvalidEnumException("Invalid media type: " + mediaRequest.getType());
                    }

                    return Media.builder()
                            .url(mediaRequest.getUrl())
                            //.post(post)
                            .type(mediaType)
                            .build();
                }).toList() : null;

        post.setMedia(medias);

        user.getPosts().add(post);

        postRepository.save(post);
        //userRepository.save(user);
        return new MessageResponse("Post created successfully.");
    }

    @Override
    public MessageResponse deletePost(int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if(!Objects.equals(post.getUser().getId(), currentUser.getId())){
            throw new AccessDeniedException("You do not have permission to delete this post");
        }
        postRepository.delete(post);
        return new MessageResponse("Post deleted successfully.");
    }

    private void validateProfileOrCoverPicture(CreatePostRequest request) {
        String type = request.getType();

        // Return early if the type is not COVER_PICTURE or PROFILE_PICTURE
        if (!EnumSet.of(EPost.COVER_PICTURE, EPost.PROFILE_PICTURE).contains(EPost.valueOf(type))) {
            return;
        }

        List<MediaRequest> medias = request.getMedias();

        if (medias.size() != 1 || !medias.get(0).getType().equals(EMedia.IMAGE.name())) {
            throw new InvalidRequestException("Profile and cover must contain exactly one picture of type IMAGE.");
        }
    }


}
