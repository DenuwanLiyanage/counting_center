package com.example.counting_center.controllers;

import com.example.counting_center.messages.BaseMessage;
import com.example.counting_center.entities.Vote;
import com.example.counting_center.messages.ValidateBallotIdRequest;
import com.example.counting_center.messages.ValidateBallotIdResponse;
import com.example.counting_center.repositories.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class VoteController {
    @Autowired
    private VoteRepository repository;

    @PostMapping("/start-voting")
    ResponseEntity<?> startVoting(@RequestBody BaseMessage<String> msg) {
        // TODO: start accepting votes
        return ResponseEntity.status(200).body(repository.findAll());
    }

    @PostMapping("/end-voting")
    ResponseEntity<?> endVoting(@RequestBody BaseMessage<String> msg) {
        // TODO: end accepting votes
        return ResponseEntity.status(200).body(repository.findAll());
    }

    @GetMapping("/vote")
    ResponseEntity<?> voteGet() {
        return ResponseEntity.status(200).body(repository.findAll());
    }

    @PostMapping("/vote")
    ResponseEntity<?> votePost(@RequestBody BaseMessage<Vote> msg) {
        log.info("vote POST");
        Vote vote = msg.getMessage();

        // TODO: validate message signature

        vote.setAccepted(false);
        vote = repository.save(vote);

        createSecureConnection();

        // TODO: send ballotId to voting center and check its status
        ValidateBallotIdRequest request = new ValidateBallotIdRequest(vote.getBallotId());
        ValidateBallotIdResponse response = new ValidateBallotIdResponse(vote.getBallotId(), "1231412");

        // TODO: validate response

        String receipt = signMessage(response.getSignedBallotId());
        vote.setAccepted(true);
        repository.save(vote);

        // TODO: send response with receipt
        return ResponseEntity.status(200).body(receipt);
    }

    @GetMapping("/check-receipt")
    ResponseEntity<?> checkReceipt(@RequestBody BaseMessage<String> msg) {
        // TODO: check receipt validity
        // TODO: send response valid vote
        return ResponseEntity.status(200).body(repository.findAll());
    }

    /**
     * ----------------------------------------------------------------------
     * Helpers
     * ----------------------------------------------------------------------
     */

    void createSecureConnection() {
        // TODO: SSL implementation
    }

    String signMessage(String message){
        // TODO: sign message
        return message;
    }

}
