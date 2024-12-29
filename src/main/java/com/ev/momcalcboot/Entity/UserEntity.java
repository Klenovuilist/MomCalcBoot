package com.ev.momcalcboot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;


import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "users")

public class UserEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password_user")
    private String passwordUser;

    @Column(name = "role_user")
    private String roleUser;

    @Column(name = "data_user")
    @CreationTimestamp
    private java.sql.Date dataUser;

//    @Column(name = "cookiesParametr")
//    @Enumerated(value = EnumType.STRING)
//    private CookiesParametr cookiesParametr;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<MaterialsEntity> materals_entities;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<SqrewEntity> sqrewEntities;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<BoltEntity> BoltEntities;

}
