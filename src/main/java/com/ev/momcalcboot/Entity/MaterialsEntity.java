package com.ev.momcalcboot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;


import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "materals_db")
@Builder
@AllArgsConstructor
@NoArgsConstructor


public class MaterialsEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "limit_strength")
    private Integer limitStrength;

    @Column(name = "materials")
    private String materials;

    @Column(name = "comments")
    private String comments;

    @Column(name = "strength_class")
    private  Double strengthClass;

    @Column(name = "k_depth")
    private Double kDepth;

    @CreatedDate
    @Column(name = "data_create")
    private LocalDateTime dataCreate;

    @Column(name = "coefffricthread")
    private Double coeffFricThread;

    @Column(name = "coefffricbolthead")
    private Double coeffFricBoltHead;

    @Column(name = "safetyfactor")
    private Double safetyFactor;

    @Column(name = "materialscrew")
    private String materialScrew;

    @Column(name = "limit_screw")
    private Integer limitScrew;

    @Column(name = "class_screw")
    private Double classScrew;

    @OneToMany(mappedBy = "materialsEntity", fetch = FetchType.LAZY)
    private List<MomentsEntity> momentsEntity;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;





//    @OneToOne
//    private Moments_db moments_db = new Moments_db();



}
