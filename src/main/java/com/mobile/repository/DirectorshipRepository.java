package com.mobile.repository;


import com.mobile.entities.Directorship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorshipRepository extends JpaRepository<Directorship, Long> {
}
