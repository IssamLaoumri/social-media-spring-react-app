package com.laoumri.springbootbackend.models;

import com.laoumri.springbootbackend.entities.Post;
import com.laoumri.springbootbackend.enums.EMedia;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaModel {
    private int id;
    private String type;
    private String url;
}
