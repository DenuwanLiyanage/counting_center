package com.example.counting_center.controllers;

import com.example.counting_center.entities.BaseMessage;
import com.example.counting_center.entities.Vote;
import com.example.counting_center.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteController {
    @Autowired
    private VoteRepository repository;

    @GetMapping("/vote")
    ResponseEntity<?> voteGet() {
        return ResponseEntity.status(200).body(repository.findAll());
    }

    @PostMapping("/vote")
    ResponseEntity<?> votePost(@RequestBody BaseMessage<Vote> msg) {

        return ResponseEntity.status(200).body(repository.save(msg.getMessage()));
    }
}
