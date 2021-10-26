package com.example.counting_center.controllers;

import com.example.counting_center.entities.Candidate;
import com.example.counting_center.messages.CandidateRequest;
import com.example.counting_center.messages.CandidateResponse;
import com.example.counting_center.messages.ErrorResponse;
import com.example.counting_center.repositories.CandidateRepository;
import com.example.counting_center.util.ErrorMessageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/candidates")
public class CandidatesController {

    @Autowired
    private CandidateRepository candidateRepository;

    @PostMapping("/register-candidate")
    ResponseEntity<?> register(@RequestBody CandidateRequest request) {
        log.info("register -> {}", request);
        Candidate candidate = new Candidate(request.getId(),request.getName());
        try {
             candidate = candidateRepository.save(candidate);
        } catch (Exception e) {
            log.info("error ",e);
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(ErrorMessageCode.INTERNAL_SERVER_ERROR));
        }

        return ResponseEntity.ok().body(new CandidateResponse(candidate.getId(), candidate.getName()));
    }

    @GetMapping("/all-candidates")
    ResponseEntity<?> getCandidates() {
        return ResponseEntity.status(200).body(candidateRepository.findAll());
    }
}
