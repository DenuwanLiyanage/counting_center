package com.example.counting_center.entities;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="votes")
@Getter
public class Vote {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    private String ballotId;

    @NotNull
    private String votedFor;

    public Vote() {
    }

    public Vote(String ballotId, String votedFor) {
        this.ballotId = ballotId;
        this.votedFor = votedFor;
    }

    public String getHash() {
        // TODO: implement hashing function
        return "123";
    }

    @Override
    public String toString() {
        return "Vote{" + "id=" + id + ", ballotId='" + ballotId + ", votedFor='" + votedFor + '}';
    }
}
