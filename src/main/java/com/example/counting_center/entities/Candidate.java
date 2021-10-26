package com.example.counting_center.entities;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class Candidate {
    private @Id
    String id;
    private String name;

    public Candidate(String id,String name) {
        this.id = id;
        this.name = name;
    }

    public Candidate() { }
}
