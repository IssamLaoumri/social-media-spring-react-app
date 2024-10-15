package com.laoumri.springbootbackend.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class RelationshipModel {
    private boolean areFriends = false;
    private boolean isFriendRequestReceived= false;
    private boolean isFriendRequestSent= false;
}
