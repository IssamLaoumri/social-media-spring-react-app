package com.laoumri.springbootbackend.repositories;

import com.laoumri.springbootbackend.entities.React;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactRepository extends JpaRepository<React, Integer> {
}
