package com.ev.momcalcboot.service.internal;

import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.Entity.MomentsEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Builder
@Data

public class TempObjectService {


    private List<MaterialsEntity> tempMaterals_entity;

    private List<MomentsEntity> tempListMoment;

    public void setParametrForTempListMaterial(MaterialsEntity materalSourse){
        while (! tempMaterals_entity.isEmpty()){

            tempMaterals_entity.remove(0);
        }

        tempMaterals_entity.add(MaterialsEntity.builder()
            .kDepth(materalSourse.getKDepth())
            .user(materalSourse.getUser())
            .materials(materalSourse.getMaterials())
            .comments(materalSourse.getComments())
            .id(materalSourse.getId())
            .momentsEntity(materalSourse.getMomentsEntity())
            .strengthClass(materalSourse.getStrengthClass())
            .limitStrength(materalSourse.getLimitStrength())
            .classScrew(materalSourse.getClassScrew())
            .limitScrew(materalSourse.getLimitScrew())
            .materialScrew(materalSourse.getMaterialScrew())
            .coeffFricThread(materalSourse.getCoeffFricThread())
            .coeffFricBoltHead(materalSourse.getCoeffFricBoltHead())
            .safetyFactor(materalSourse.getSafetyFactor())
            .build());
    }

    public void removeTempListMaterial(){
        while (! tempMaterals_entity.isEmpty()){
            tempMaterals_entity.remove(0);
        }
    }

    public void setMomentsForTempListMoment(List<MomentsEntity> listMomentSours){

        while (! tempListMoment.isEmpty()){
            tempListMoment.remove(0);
        }
        tempListMoment.addAll(listMomentSours);
    }

    public void removeTempListMoment(){
        while (! tempListMoment.isEmpty()){
            tempListMoment.remove(0);
        }
    }
}


