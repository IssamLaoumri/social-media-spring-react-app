package com.laoumri.springbootbackend.repositories;

import com.laoumri.springbootbackend.entities.React;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactRepository extends JpaRepository<React, Integer> {
    Optional<React> findById(int id);
}
