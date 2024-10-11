package com.laoumri.springbootbackend.dto.requests;

import com.laoumri.springbootbackend.entities.Media;
import com.laoumri.springbootbackend.enums.EPostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {
    @NotBlank
    private String type;
    @NotEmpty
    private String content;
    @Size(min=1)
    private List<MediaRequest> medias;
}
