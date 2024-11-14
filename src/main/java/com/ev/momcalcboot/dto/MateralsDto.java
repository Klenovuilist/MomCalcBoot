package com.ev.momcalcboot.dto;

import com.ev.momcalcboot.Entity.MomentsEntity;
import com.ev.momcalcboot.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor


public class MateralsDto {


    private int id;

    private int limitStrength;

    private String materials;

    private String comments;

    private  Double strengthClass;

    private List<MomentsEntity> moments_entity;

    private UserEntity userEntity;

    private String limitName;

    private String materialsName;

    private String commentsName;

    private  String className;
}
