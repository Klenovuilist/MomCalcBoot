package com.ev.momcalcboot.dto;

import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.Entity.SqrewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

    public class UserDto {

    private int id;

    private String userName;

    private String passwordUser;

    private String roleUser;

    private java.sql.Date dataUser;

    private List<String> userInfo;

    private List<MaterialsEntity> userMaterials;

    private List<SqrewEntity> userSqrews;

    private List<BoltEntity> userBolts;
}
