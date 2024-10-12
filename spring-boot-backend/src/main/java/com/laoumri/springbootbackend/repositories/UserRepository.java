package com.laoumri.springbootbackend.repositories;

import com.laoumri.springbootbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(int id);
    boolean existsByEmail(String email);
    boolean existsBy_username(String username);
}
