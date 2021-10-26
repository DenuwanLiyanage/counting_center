package com.example.counting_center.repositories;

import com.example.counting_center.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Override
    List<Candidate> findAll();
}
