package com.example.counting_center.web_clients;

import com.example.counting_center.messages.ValidateBallotIdRequest;
import com.example.counting_center.messages.ValidateBallotIdResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BallotIdVerificationWebClient {

   public ValidateBallotIdResponse getVerifiedBallotId(ValidateBallotIdRequest validateBallotIdRequest){
       WebClient webClient = WebClient.create("http://localhost:8080");

      return webClient.post()
               .uri("/ballot-id")
               .retrieve()
               .bodyToMono(ValidateBallotIdResponse.class)
               .block();
   }
}