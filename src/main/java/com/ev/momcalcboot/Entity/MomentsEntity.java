package com.ev.momcalcboot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "moments_db")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class MomentsEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "moments_nm")
    private double momentsNm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id", referencedColumnName = "id")
    private ThreadEntity thread;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materals_id", referencedColumnName = "id")
    private MaterialsEntity materialsEntity;



//    public Moments_db(double moments_nm, Thread_db thread, Materals_db materals_db) {
//        this.moments_nm = moments_nm;
//        this.thread = thread;
//        this.materals_db = materals_db;
//    }

    //    @OneToOne
//    @JoinColumn(name = "materials_id")
//    private Materals_db materals_db;
}
