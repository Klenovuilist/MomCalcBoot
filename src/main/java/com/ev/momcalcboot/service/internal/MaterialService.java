package com.ev.momcalcboot.service.internal;

import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.Entity.SqrewEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import static com.ev.momcalcboot.service.internal.ParserNumber.toDouble;
import static com.ev.momcalcboot.service.internal.ParserNumber.toInt;
import java.util.Map;

@Service
public class MaterialService {

    public MaterialsEntity getMaterialByRequestParam(Map<String, String> dataFromForm) {

        return MaterialsEntity.builder()
                .coeffFricBoltHead(toDouble(dataFromForm.get("coefficientOfFrictionBoltHead")))
                .coeffFricThread(toDouble(dataFromForm.get("coefficientOfFrictionThread")))
                .safetyFactor(toDouble(dataFromForm.get("safetyFactor")))
                .build();
    }

    public MaterialsEntity getMaterialParamDefoult(){
        return MaterialsEntity.builder()
                .coeffFricBoltHead(0.14)
                .coeffFricThread(0.14)
                .safetyFactor(0.7)
                .build();
    }



    }



