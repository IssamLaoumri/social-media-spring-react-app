package com.laoumri.springbootbackend.entities;

import com.laoumri.springbootbackend.enums.EMediaType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Media {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private EMediaType type;
    private String url;
    @ManyToOne
    private Post post;
}
