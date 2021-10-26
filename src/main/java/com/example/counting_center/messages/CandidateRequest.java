package com.example.counting_center.messages;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public class CandidateRequest {

    @NotNull
    private String id;

    @NotNull
    private String name;

}
