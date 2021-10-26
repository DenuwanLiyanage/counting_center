package com.example.counting_center.controllers;

import com.example.counting_center.messages.*;
import com.example.counting_center.entities.Vote;
import com.example.counting_center.repositories.VoteRepository;
import com.example.counting_center.util.ErrorMessageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    private VoteRepository repository;

    /**
     * Get all votes (For Testing only).
     *
     * @return List of votes
     */
    @GetMapping("/votes")
    ResponseEntity<?> voteGet() {
        return ResponseEntity.status(200).body(repository.findAll());
    }


    /**
     * Vote request called from mobile app
     *
     * @param msg VoteRequest
     * @return vote receipt if successful else respective error message
     */
    @PostMapping("/vote")
    ResponseEntity<?> votePost(@Valid @RequestBody VoteRequest msg) {
        log.info("vote POST -> {}", msg);

        // TODO: validate message signature
        boolean isSignatureValid = true;
        if (!isSignatureValid) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(ErrorMessageCode.INVALID_SIGNATURE));
        }

        Vote vote = new Vote(msg.getMessage().getBallotID(), msg.getMessage().getVoteFor());
        try {
            vote = repository.save(vote);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(ErrorMessageCode.INTERNAL_SERVER_ERROR));
        }

        createSecureConnection();

        // TODO: send ballotId to voting center and check its status
        ValidateBallotIdRequest request = new ValidateBallotIdRequest(vote.getBallotId());
        ValidateBallotIdResponse response = new ValidateBallotIdResponse(vote.getBallotId(), "1231412");

        // TODO: validate response
        boolean isValidBallotID = true;
        if (!isValidBallotID) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(ErrorMessageCode.INVALID_BALLOT_ID));
        }

        String receipt = signMessage(response.getSignedBallotId());
        vote.setAccepted(true);
        vote.setReceipt(receipt);
        try {
            vote = repository.save(vote);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(ErrorMessageCode.INTERNAL_SERVER_ERROR));
        }

        return ResponseEntity.ok().body(new VoteSuccessResponse(vote.getReceipt()));
    }

    /**
     * Check whether the votes marked
     *
     * @param msg vote receipt
     * @return success if valid vote
     */
    @PostMapping("/check")
    ResponseEntity<?> checkReceipt(@RequestBody String msg) {
        // TODO: check receipt validity
        // TODO: send response valid vote
        return ResponseEntity.ok().body("");
    }

    /**
     * ----------------------------------------------------------------------
     * Helpers
     * ----------------------------------------------------------------------
     */

    void createSecureConnection() {
        // TODO: SSL implementation
    }

    String signMessage(String message) {
        // TODO: sign message
        return message;
    }

}
