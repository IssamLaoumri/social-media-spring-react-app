package com.laoumri.springbootbackend.servicesImpl;

import com.laoumri.springbootbackend.Exceptions.InvalidEnumException;
import com.laoumri.springbootbackend.Exceptions.ResourceNotFoundException;
import com.laoumri.springbootbackend.entities.Post;
import com.laoumri.springbootbackend.entities.React;
import com.laoumri.springbootbackend.entities.User;
import com.laoumri.springbootbackend.enums.EReact;
import com.laoumri.springbootbackend.repositories.PostRepository;
import com.laoumri.springbootbackend.repositories.ReactRepository;
import com.laoumri.springbootbackend.repositories.UserRepository;
import com.laoumri.springbootbackend.services.ReactService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ReactServiceImpl implements ReactService {
    private final ReactRepository reactRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    @Override
    public void reactToPost(int postId, String reactType) {
        EReact eReact;
        try {
            eReact = EReact.valueOf(reactType);
        }catch (IllegalArgumentException ex) {
            throw new InvalidEnumException("Invalid react type: " + reactType);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        React react = React.builder()
                .reactedPost(post)
                .reactedBy(currentUser)
                .type(eReact)
                .build();
        reactRepository.save(react);
    }
}
