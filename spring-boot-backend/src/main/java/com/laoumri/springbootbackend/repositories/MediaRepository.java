package com.laoumri.springbootbackend.repositories;

import com.laoumri.springbootbackend.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Integer> {
}
