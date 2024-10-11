package com.laoumri.springbootbackend.repositories;

import com.laoumri.springbootbackend.entities.Role;
import com.laoumri.springbootbackend.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
