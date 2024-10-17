package com.laoumri.springbootbackend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean isUpdated;
    private String content;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Media media;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User commentBy;
    private Instant publishedAt;
}
