package com.example.counting_center.controllers;

import com.example.counting_center.entities.Candidate;
import com.example.counting_center.entities.ElectionResult;
import com.example.counting_center.messages.ErrorResponse;
import com.example.counting_center.repositories.CandidateRepository;
import com.example.counting_center.repositories.ResultRepository;
import com.example.counting_center.repositories.VoteRepository;
import com.example.counting_center.util.ErrorMessageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/counting-center")
public class CountingCenterController {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private ResultRepository resultRepository;

    @PostMapping("/start-voting")
    ResponseEntity<?> startVoting(@RequestBody String msg) {
        // TODO: start accepting votes
        return ResponseEntity.status(200).body("success");
    }

    @PostMapping("/end-voting")
    ResponseEntity<?> endVoting(@RequestBody String msg) {
        // TODO: end accepting votes
        List<Candidate> candidateList;
        try{
            candidateList = candidateRepository.findAll();
        }catch (Exception e){
            log.error("Can't Get all Candidates");
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(ErrorMessageCode.INTERNAL_SERVER_ERROR));
        }

        try {
            for (int i = 0; i < candidateList.size(); i++) {
                ElectionResult electionResult = new ElectionResult();
                Long count = voteRepository.countAllByVotedForAndAcceptedTrue(candidateList.get(i).getId());
                electionResult.setCandidateID(candidateList.get(i).getId());
                electionResult.setCount(count);
                resultRepository.save(electionResult);
            }
        }catch (Exception e){
            log.error("Error occur while counting");
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(ErrorMessageCode.INTERNAL_SERVER_ERROR));
        }
        try{
            return ResponseEntity.status(200).body(resultRepository.findAll());
        }catch (Exception e){
            log.error("Can't Get all Results");
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(ErrorMessageCode.INTERNAL_SERVER_ERROR));
        }

    }
}
