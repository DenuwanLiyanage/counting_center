package com.example.counting_center.controllers;

import com.example.counting_center.messages.*;
import com.example.counting_center.entities.Vote;
import com.example.counting_center.repositories.VoteRepository;
import com.example.counting_center.util.ErrorMessageCode;

import com.example.counting_center.web_clients.BallotIdVerificationWebClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    private VoteRepository repository;

    @Autowired
    private BallotIdVerificationWebClient ballotIdVerificationWebClient;
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
    ResponseEntity<?> votePost(@Valid @RequestBody String msg) {
        log.info("vote POST -> {}", msg);

        ObjectMapper objectMapper = new ObjectMapper();
        VoteRequest voteRequest = new VoteRequest();
        try{
            voteRequest = objectMapper.readValue(msg,VoteRequest.class);
        } catch (Exception e){
            log.info("unable to convert to VoteRequest ", e);
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(ErrorMessageCode.INVALID_REQUEST));
        }



        // TODO: validate message signature
        boolean isSignatureValid = true;
        if (!isSignatureValid) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(ErrorMessageCode.INVALID_SIGNATURE));
        }

        Vote vote = new Vote(voteRequest.getMessage().getBallotID(), voteRequest.getMessage().getVoteFor());
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

        try{
            ValidateBallotIdResponse response1 = ballotIdVerificationWebClient.getVerifiedBallotId(request);
        }catch (Exception e){
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(ErrorMessageCode.INTERNAL_SERVER_ERROR));
        }

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
