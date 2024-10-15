package com.laoumri.springbootbackend.repositories;

import com.laoumri.springbootbackend.entities.FriendRequest;
import com.laoumri.springbootbackend.entities.User;
import com.laoumri.springbootbackend.enums.FriendRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    Optional<FriendRequest> findByRequesterAndRequested(User requester, User requested);
    Optional<FriendRequest> findByRequesterAndRequestedAndStatus(User requester, User requested, FriendRequestStatus status);

}
