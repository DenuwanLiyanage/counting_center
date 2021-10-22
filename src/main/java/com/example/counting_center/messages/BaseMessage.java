package com.example.counting_center.messages;

import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class BaseMessage<T> {
    @NotNull
    private T message;

    @NotNull
    private String signature;
}
