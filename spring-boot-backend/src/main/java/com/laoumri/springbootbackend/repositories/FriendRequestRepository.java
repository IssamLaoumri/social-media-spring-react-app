package com.laoumri.springbootbackend.repositories;

import com.laoumri.springbootbackend.entities.FriendRequest;
import com.laoumri.springbootbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    Optional<FriendRequest> findByRequesterAndRequested(User requester, User requested);
}
