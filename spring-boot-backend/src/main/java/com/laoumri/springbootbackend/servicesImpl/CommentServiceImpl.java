package com.laoumri.springbootbackend.servicesImpl;

import com.laoumri.springbootbackend.Exceptions.AccessDeniedException;
import com.laoumri.springbootbackend.Exceptions.ResourceNotFoundException;
import com.laoumri.springbootbackend.dto.requests.CreateCommentRequest;
import com.laoumri.springbootbackend.dto.responses.MessageResponse;
import com.laoumri.springbootbackend.entities.Comment;
import com.laoumri.springbootbackend.entities.Media;
import com.laoumri.springbootbackend.entities.Post;
import com.laoumri.springbootbackend.entities.User;
import com.laoumri.springbootbackend.enums.EMedia;
import com.laoumri.springbootbackend.repositories.CommentRepository;
import com.laoumri.springbootbackend.repositories.MediaRepository;
import com.laoumri.springbootbackend.repositories.PostRepository;
import com.laoumri.springbootbackend.repositories.UserRepository;
import com.laoumri.springbootbackend.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final MediaRepository mediaRepository;

    @Override
    public MessageResponse addComment(int postId, CreateCommentRequest request) {
        Post relatedPost = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        Media media = Media.builder()
                .type(EMedia.IMAGE)
                .url(request.getMediaUrl())
                .build();

        Comment comment = Comment.builder()
                .isUpdated(false)
                .commentBy(currentUser)
                .post(relatedPost)
                .media(media)
                .publishedAt(Instant.now())
                .content(request.getContent())
                .build();
        mediaRepository.save(media);
        commentRepository.save(comment);
        return new MessageResponse("Comment added successfully");
    }

    @Override
    public MessageResponse deleteComment(int commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment not found"));
        if (!comment.getCommentBy().equals(currentUser)) {
            throw new AccessDeniedException("Comment not belong to current user");
        }
        commentRepository.delete(comment);
        return new MessageResponse("Comment deleted successfully");
    }

    @Override
    public MessageResponse updateComment(int commentId, CreateCommentRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment not found"));
        if (!comment.getCommentBy().equals(currentUser)) {
            throw new AccessDeniedException("Comment not belong to current user");
        }
        // Complete tomorrow
        return null;
    }

}
