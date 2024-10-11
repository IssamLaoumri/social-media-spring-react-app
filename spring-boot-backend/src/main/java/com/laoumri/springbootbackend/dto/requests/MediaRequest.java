package com.laoumri.springbootbackend.dto.requests;

import com.laoumri.springbootbackend.enums.EMediaType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaRequest {
    private String type;
    private String url;
}
