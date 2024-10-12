package com.laoumri.springbootbackend.entities;

import com.laoumri.springbootbackend.enums.EPost;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "posts")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private EPost type;
    private String content;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Media> media;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Instant publishedAt;
}
