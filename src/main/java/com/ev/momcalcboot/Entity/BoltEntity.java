package com.ev.momcalcboot.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity()
@Table(name = "bolt_entity")
@Builder

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

    public BoltEntity(int id, String name, Integer limit, String comment, LocalDateTime dataCreate, Double classBolt, UserEntity user) {
        this.id = id;
        this.name = name;
        this.limit = limit;
        this.comment = comment;
        this.dataCreate = dataCreate;
        this.classBolt = classBolt;
        this.user = user;
    }

    public BoltEntity() {
    }

    @Override
    public String toString() {
        return "BoltEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", limit=" + limit +
                '}';
    }

    protected boolean canEqual(final Object other) {

        return other instanceof BoltEntity;
    }

}



//name limit comment dataCreate classBolt