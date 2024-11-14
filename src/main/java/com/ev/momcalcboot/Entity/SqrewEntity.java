package com.ev.momcalcboot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity()
@Table(name = "sqrew_entity")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SqrewEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sqrew_name")
    private String name;

    @Column(name = "sqrew_limit")
    private Integer limit;

    @Column(name = "comment")
    private String comment;

    @Column(name = "sqrew_depth")
    private Double depth;

    @Column(name = "data_create")
    @CreationTimestamp
    private LocalDateTime dataCreate;

    @Column(name = "classsqrew")
    private Double classSqrew;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private UserEntity user;

}
