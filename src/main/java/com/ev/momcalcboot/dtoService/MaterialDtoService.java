package com.ev.momcalcboot.dtoService;

import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.dao.MaterialsDao;

import com.ev.momcalcboot.dto.MateralsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor

@Component

public class MaterialDtoService {

private  final MaterialsDao materals_dao;


public List<MateralsDto> getMaterialDtoByUserId(int id){

    List<MaterialsEntity> materals_entities = materals_dao.getMaterialsByUserId(id);

    List<MateralsDto> materalsDto = new ArrayList<>();

    for (MaterialsEntity materal: materals_entities){
        MateralsDto materalDto = MateralsDto.builder()
                .materials(materal.getMaterials())
                .userEntity(materal.getUser())
                .comments(materal.getComments())
                .id(materal.getId())
                .limitStrength(materal.getLimitStrength())
                .strengthClass(materal.getStrengthClass())
                .build();

        materalDto.setCommentsName("comments" + materalDto.getId());

        materalDto.setLimitName("limit" + materalDto.getId());

        materalDto.setMaterialsName("material" + materalDto.getId());

        materalDto.setClassName("class" + materalDto.getId());

    materalsDto.add(materalDto);
    }
    return materalsDto;
}


}
