package com.ev.momcalcboot.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ThreadDTO {

    List<String> parametrThread;

    String threadName;

    @Override
    public String toString() {
        return "ThreadDTO{" +
                "threadName='" + threadName + '\'' +
                '}';
    }
}
