package com.laoumri.springbootbackend.dto.requests;

import com.laoumri.springbootbackend.entities.Post;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateCommentRequest {
    private String content;
    private String mediaUrl;
}
