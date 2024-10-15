package com.laoumri.springbootbackend.models;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostModel {
    private int id;
    private String type;
    private String content;
    private List<MediaModel> media;
    private Instant publishedAt;
}
