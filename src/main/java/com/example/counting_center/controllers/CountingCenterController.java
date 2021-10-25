package com.example.counting_center.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counting-center")
public class CountingCenterController {

    @PostMapping("/start-voting")
    ResponseEntity<?> startVoting(@RequestBody String msg) {
        // TODO: start accepting votes
        return ResponseEntity.status(200).body("success");
    }

    @PostMapping("/end-voting")
    ResponseEntity<?> endVoting(@RequestBody String msg) {
        // TODO: end accepting votes
        return ResponseEntity.status(200).body("success");
    }
}
