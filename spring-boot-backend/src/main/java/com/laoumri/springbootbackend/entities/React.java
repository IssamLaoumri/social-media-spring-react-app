package com.laoumri.springbootbackend.entities;

import com.laoumri.springbootbackend.enums.EReact;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class React {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private EReact type;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Post reactedPost;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User reactedBy;
}
