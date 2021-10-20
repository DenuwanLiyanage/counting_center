package com.example.counting_center.entities;

import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class BaseMessage<T> {
    @NotNull
    private T message;

    @NotNull
    private String hash;
}
