package com.ev.momcalcboot.service.internal;

import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.Entity.SqrewEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MaterialService {



    public MaterialsEntity getMaterialByRequestParam(Map<String, String> dataFromForm) {

        return MaterialsEntity.builder()
                .coeffFricBoltHead(Double.parseDouble(dataFromForm.get("coefficientOfFrictionBoltHead")))
                .coeffFricThread(Double.parseDouble(dataFromForm.get("coefficientOfFrictionThread")))
                .safetyFactor(Double.parseDouble(dataFromForm.get("safetyFactor")))
                .build();

        //        dataForm.put("limateStrengthBolt_Mpa", null);
//        dataForm.put("limateStrengthScrew_Mpa", null);
//        dataForm.put("diametrThread_mm", null);
//        dataForm.put("threadIdForm", null);
//        dataForm.put("threadName", null);
//        dataForm.put("middleDiamThread_mm", null);
//        dataForm.put("k_threadDepth", null);
//        dataForm.put("safetyFactor", null);
//        dataForm.put("powerMaxForMaterial_kgs", null);
//
//        dataForm.put("stepThread_mm", null);
//        dataForm.put("coefficientOfFrictionThread", null);
//        dataForm.put("coefficientOfFrictionBoltHead", null);
//        dataForm.put("diametrHead_mm", null);
//        dataForm.put("diametrHole_mm", null);
//        dataForm.put("momentKellerman_NM", null);


    }

    public MaterialsEntity getMaterialParamDefoult(){
        return MaterialsEntity.builder()
                .coeffFricBoltHead(0.14)
                .coeffFricThread(0.14)
                .safetyFactor(0.7)
                .build();
    }



    }



