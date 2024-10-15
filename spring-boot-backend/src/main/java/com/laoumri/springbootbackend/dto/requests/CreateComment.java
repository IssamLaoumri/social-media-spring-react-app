package com.laoumri.springbootbackend.dto.requests;

import com.laoumri.springbootbackend.entities.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateComment {
    private int id;
    private String content;
    private String media;
    private Post post;
    private Instant publishedAt;
}
