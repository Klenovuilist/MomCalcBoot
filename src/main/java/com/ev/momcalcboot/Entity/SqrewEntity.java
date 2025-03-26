package com.ev.momcalcboot.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity()
@Table(name = "sqrew_entity")
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

    @Transient
    private String dataCreatePars;


    protected boolean canEqual(final Object other) {
        return other instanceof SqrewEntity;
    }

}
