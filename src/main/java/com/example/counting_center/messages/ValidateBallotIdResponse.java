package com.example.counting_center.messages;

import lombok.Getter;

@Getter
public class ValidateBallotIdResponse {
   private String ballotId;
   private String signedBallotId;

   public ValidateBallotIdResponse(String ballotId, String signedBallotId) {
      this.ballotId = ballotId;
      this.signedBallotId = signedBallotId;
   }
}
