package com.example.counting_center.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class Vote {
    private @Id
    @GeneratedValue
    Long id;

    @NotNull
    private String ballotId;

    @NotNull
    private String votedFor;

    @Setter
    private boolean accepted;

    public Vote() {
    }

    public String getHash() {
        // TODO: implement hashing function
        return "123";
    }

    @Override
    public String toString() {
        return "Vote{" + "id=" + id + ", ballotId=" + ballotId + ", votedFor=" + votedFor + ", accepted=" + accepted +
                "}";
    }
}
