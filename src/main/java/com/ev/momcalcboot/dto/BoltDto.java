package com.ev.momcalcboot.dto;

import com.ev.momcalcboot.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class BoltDto {

    private int id;

    private String name;

    private Integer limit;


    private String comment;


    private LocalDateTime dataCreate;


    private Double classBolt;



    private UserEntity user;
}
