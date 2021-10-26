package com.example.counting_center.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Session {
    private @Id
    @GeneratedValue
    Long id;

    private String key;

    public Session(String key){
        this.key = key;
    }
}
