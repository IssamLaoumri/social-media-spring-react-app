package com.laoumri.springbootbackend.servicesImpl;

import com.laoumri.springbootbackend.dto.requests.CreatePostRequest;
import com.laoumri.springbootbackend.dto.responses.MessageResponse;
import com.laoumri.springbootbackend.entities.Media;
import com.laoumri.springbootbackend.entities.Post;
import com.laoumri.springbootbackend.entities.User;
import com.laoumri.springbootbackend.enums.EMediaType;
import com.laoumri.springbootbackend.enums.EPostType;
import com.laoumri.springbootbackend.repositories.PostRepository;
import com.laoumri.springbootbackend.repositories.UserRepository;
import com.laoumri.springbootbackend.services.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

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
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
        Post post = Post.builder()
                .content(request.getContent())
                .user(user)
                .type(EPostType.valueOf(request.getType()))
                .publishedAt(Instant.now())
                .build();
        List<Media> medias = request.getMedias().stream()
                .map(mediaRequest -> Media.builder()
                        .url(mediaRequest.getUrl())
                        .post(post)
                        .type(EMediaType.valueOf(mediaRequest.getType()))
                        .build()).toList();
        post.setMedia(medias);
        postRepository.save(post);
        return new MessageResponse("Post created successfully.");
    }
}
