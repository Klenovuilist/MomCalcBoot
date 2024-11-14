package com.ev.momcalcboot.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "thread_db")
public class ThreadEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

    @Column(name = "thread")
    private String thread;

    @Column(name = "d_hole")
    private Double dhole_mm;

    @Column(name = "d_head")
    private Double dHead_mm;

    @Column(name = "step_thread")
    private Double StepThread_mm;

    @Column(name = "d_midlethread")
    private Double dMidlethread_mm;

    @Column(name = "d_bolt")
    private Double dBolt_mm;

    @OneToMany(mappedBy = "thread",fetch = FetchType.LAZY)
    private List<MomentsEntity> moments_entity;

//    @Column(name = "step_thread")
//    private  String stepThread;
}
