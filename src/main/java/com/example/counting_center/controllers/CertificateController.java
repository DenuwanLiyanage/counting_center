package com.example.counting_center.controllers;

import com.example.counting_center.messages.ErrorResponse;
import com.example.counting_center.util.ErrorMessageCode;
import com.example.counting_center.util.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/certificate")
public class CertificateController {
    private final Keys keys;

    CertificateController(){
        this.keys = new Keys();
    }

    @GetMapping("/sign")
    ResponseEntity<?> getSignCertificate() {

        String certificate;
        try {
            certificate = keys.getSignCertificateString();
        } catch (Exception e) {
            log.error("Get signing cert error", e);
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, ErrorMessageCode.INTERNAL_SERVER_ERROR));
        }
        return ResponseEntity.ok().body(certificate);
    }

    @GetMapping("/encrypt")
    ResponseEntity<?> getEncryptCertificate() {
        String certificate;
        try {
            certificate = keys.getEncryptCertificateString();
        } catch (Exception e) {
            log.error("Get signing cert error", e);
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, ErrorMessageCode.INTERNAL_SERVER_ERROR));
        }
        return ResponseEntity.ok().body(certificate);
    }
}
