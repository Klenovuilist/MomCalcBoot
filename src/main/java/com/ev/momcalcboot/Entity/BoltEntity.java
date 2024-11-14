package com.ev.momcalcboot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity()
@Table(name = "bolt_entity")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BoltEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "bolt_name")
    private String name;

    @Column(name = "bolt_limit")
    private Integer limit;

    @Column(name = "comment")
    private String comment;

    @Column(name = "data_create")
    @CreationTimestamp
    private LocalDateTime dataCreate;

    @Column(name = "classbolt")
    private Double classBolt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private UserEntity user;

}

//name limit comment dataCreate classBolt