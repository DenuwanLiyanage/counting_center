package com.example.counting_center.repositories;

import com.example.counting_center.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Long countAllByVotedForAndAcceptedTrue(String votedFor);
}
