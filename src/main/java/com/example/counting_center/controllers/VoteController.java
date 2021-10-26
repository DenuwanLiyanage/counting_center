package com.example.counting_center.controllers;

import com.example.counting_center.entities.Vote;
import com.example.counting_center.messages.*;
import com.example.counting_center.repositories.VoteRepository;
import com.example.counting_center.util.ErrorMessageCode;
import com.example.counting_center.web_clients.BallotIdVerificationWebClient;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private BallotIdVerificationWebClient ballotIdVerificationWebClient;
    /**
     * Get all votes (For Testing only).
     *
     * @return List of votes
     */
    @GetMapping("/votes")
    ResponseEntity<?> voteGet() {
        try{
            log.info("Getting all votes");
            return ResponseEntity.status(200).body(repository.findAll());
        }catch (Exception e){
            log.error("Can't Get all Votes");
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(ErrorMessageCode.INTERNAL_SERVER_ERROR));
        }

    }


    /**
     * Vote request called from mobile app
     *
     * @param msg VoteRequest
     * @return vote receipt if successful else respective error message
     */
    @PostMapping("/vote")
    ResponseEntity<?> votePost(@Valid @RequestBody String msg) {
        ObjectMapper objectMapper = new ObjectMapper();
        VoteRequest voteRequest = new VoteRequest();
        try{
            voteRequest = objectMapper.readValue(msg,VoteRequest.class);
        } catch (Exception e){
            log.error("unable to convert to VoteRequest ");
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(ErrorMessageCode.INVALID_REQUEST));
        }


        if (isAccepted(voteRequest.getMessage().getBallotID())){
            log.error("Cannot vote for this ballot ID");
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
            log.error("unable to save Vote");
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(ErrorMessageCode.INTERNAL_SERVER_ERROR));
        }

        createSecureConnection();

        // TODO: send ballotId to voting center and check its status

        ValidateBallotIdRequest request = new ValidateBallotIdRequest(vote.getBallotId());
        ValidateBallotIdResponse response = new ValidateBallotIdResponse(vote.getBallotId(), "1231412");

        // TODO: Webclient done. Have to test
//        try{
//            ValidateBallotIdResponse response1 = ballotIdVerificationWebClient.getVerifiedBallotId(request);
//        }catch (Exception e){
//        log.info("unable to Connect to voting center");
//            return ResponseEntity.internalServerError()
//                    .body(new ErrorResponse(ErrorMessageCode.INTERNAL_SERVER_ERROR));
//        }


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
            log.error("unable to save Vote");
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(ErrorMessageCode.INTERNAL_SERVER_ERROR));
        }
        log.info("Successfully Voted");
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

    boolean isAccepted(String ballotID){
        if(repository.countAllByBallotIdAndAcceptedTrue(ballotID) == 0) {
            return false;
        }
        return true;
    }
    String signMessage(String message) {
        // TODO: sign message
        return message;
    }

}
