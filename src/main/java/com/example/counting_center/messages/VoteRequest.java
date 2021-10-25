package com.example.counting_center.messages;

import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
public class VoteRequest {
    @Valid
    @NotNull
    private VoteRequestDetails message;

    @NotNull
    @NotBlank
    private String signature;

    @Getter
    @ToString
    public class VoteRequestDetails {
        @NotNull
        @NotBlank
        private String ballotID;

        @NotNull
        @NotBlank
        private String voteFor;
    }
}