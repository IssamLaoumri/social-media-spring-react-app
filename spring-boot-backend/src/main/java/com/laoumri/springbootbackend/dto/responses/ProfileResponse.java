package com.laoumri.springbootbackend.dto.responses;

import com.laoumri.springbootbackend.models.PostModel;
import com.laoumri.springbootbackend.models.RelationshipModel;
import com.laoumri.springbootbackend.models.FriendModel;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponse {
    private String username;
    private String firstname;
    private String lastname;
    private List<PostModel> posts;
    private Set<FriendModel> friends;
    private RelationshipModel relationship;
    private boolean isAccountDisabled;
}
