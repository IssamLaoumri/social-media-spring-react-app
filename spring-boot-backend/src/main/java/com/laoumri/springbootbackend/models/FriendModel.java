package com.laoumri.springbootbackend.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendModel {
    private String username;
    private String firstname;
    private String lastname;
}
