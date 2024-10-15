package com.laoumri.springbootbackend.entities;

import com.laoumri.springbootbackend.enums.FriendRequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "friend_requests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    @ManyToOne
    @JoinColumn(name = "requested_id")
    private User requested;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private FriendRequestStatus status;
    private Instant requestedAt;
}
