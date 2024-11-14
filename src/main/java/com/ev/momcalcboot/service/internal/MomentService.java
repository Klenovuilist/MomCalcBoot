package com.ev.momcalcboot.service.internal;

import com.ev.momcalcboot.Entity.*;
import com.ev.momcalcboot.exceptions.FormatException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Data
@Service
@AllArgsConstructor

public class MomentService {

    public final MaterialService materialService;

    public  final BoltService boltService;

    private final SqrewService sqrewService;

    private final ThreadService threadService;


   private final double Km = 0.6; //коэфф. неравномерности нагрузки на резьбу

   private final double K = 0.87;  //коэфф. колноты резьбы


    /**
     * Расчет момента по формуле Биргера
     */
    public Double momentBirger_NM(int power_N,
                                  double middleDiamThread_mm,
                                  double stepThread_mm,
                                  double coefficientOfFrictionThread,
                                  double coefficientOfFrictionBoltHead,
                                  double diametrHead_mm,
                                  double diametrHole_mm) {

        double middleDiamThread_m = middleDiamThread_mm * 0.001;
        double stepThread_m = stepThread_mm * 0.001;
//    double coefficientOfFrictionThread
//    double coefficientOfFrictionBoltHead
        double diametrHead_m = diametrHead_mm * 0.001;
        double diametrHole_m = diametrHole_mm * 0.001;


        double radFriction_m = (Math.pow(diametrHead_m, 3) - Math.pow(diametrHole_m, 3))
                / 3 * ((Math.pow(diametrHead_m, 2) - Math.pow(diametrHole_m, 2)));

        return power_N * (((stepThread_m + Math.PI * coefficientOfFrictionThread * middleDiamThread_m)
                / 2 * (Math.PI - coefficientOfFrictionThread * stepThread_m / middleDiamThread_m))
                + coefficientOfFrictionBoltHead * radFriction_m);

    }
    /**
     * Расчет момента по формуле Каллермана - Кляйна
     */
    public Double momentKellerman_NM(int power_N
            , double middleDiamThread_mm
            , double stepThread_mm
            , double coefficientOfFrictionThread
            , double coefficientOfFrictionBoltHead
            , double diametrHead_mm
            , double diametrHole_mm) {

        double middleDiamThread_m = middleDiamThread_mm * 0.001;
        double stepThread_m = stepThread_mm * 0.001;
        double diametrHead_m = diametrHead_mm * 0.001;
        double diametrHole_m = diametrHole_mm * 0.001;

        double momentDouble_NM = Math.round((power_N * (0.5 * (stepThread_m + 1.154 * 3.14 * coefficientOfFrictionThread * middleDiamThread_m)
                / (3.14 - 1.154 * coefficientOfFrictionThread * stepThread_m / middleDiamThread_m)
                + coefficientOfFrictionBoltHead * (diametrHead_m + diametrHole_m) * 0.25)) * 10);

        int momentInt_NM = (int) momentDouble_NM;

        return (double)momentInt_NM * 0.1;



//        return (Math.round((power_N * (0.5 * (stepThread_m + 1.154 * 3.14 * coefficientOfFrictionThread * middleDiamThread_m)
//                / (3.14 - 1.154 * coefficientOfFrictionThread * stepThread_m / middleDiamThread_m)
//                + coefficientOfFrictionBoltHead * (diametrHead_m + diametrHole_m) * 0.25)) * 10)) * 0.1;
    }
    /**
     * Расчет силы затяжки по формуле Каллермана Кляйна (по моменту затяжки)
     */
    public int powerByKallerman_N(
                double moment_Nm
            , double middleDiamThread_mm
            , double stepThread_mm
            , double coefficientOfFrictionThread
            , double coefficientOfFrictionBoltHead
            , double diametrHead_mm
            , double diametrHole_mm) {

        double middleDiamThread_m = middleDiamThread_mm * 0.001;
        double stepThread_m = stepThread_mm * 0.001;
        double diametrHead_m = diametrHead_mm * 0.001;
        double diametrHole_m = diametrHole_mm * 0.001;


        return (int)(moment_Nm / (0.5 * (stepThread_m + 1.154 * 3.14 * coefficientOfFrictionThread * middleDiamThread_m)
                / (3.14 - 1.154 * coefficientOfFrictionThread * stepThread_m / middleDiamThread_m)
                + coefficientOfFrictionBoltHead * (diametrHead_m + diametrHole_m) * 0.25));
    }

    public int powerKellerman_kgs(int power_N){
        return (int)(power_N * 0.1);
    }

    public Double power_N(double moment_NM
            , double middleDiamThread_mm
            , double stepThread_mm
            , double coefficientOfFrictionThread
            , double coefficientOfFrictionBoltHead
            , double diametrHead_mm
            , double diametrHole_mm) {

        double middleDiamThread_m = middleDiamThread_mm * 0.001;
        double stepThread_m = stepThread_mm * 0.001;
//    double coefficientOfFrictionThread
//    double coefficientOfFrictionBoltHead
        double diametrHead_m = diametrHead_mm * 0.001;
        double diametrHole_m = diametrHole_mm * 0.001;


        double radFriction_m = (Math.pow(diametrHead_m, 3) - Math.pow(diametrHole_m, 3))
                / 3 * ((Math.pow(diametrHead_m, 2) - Math.pow(diametrHole_m, 2)));

        return moment_NM / (((stepThread_m + Math.PI * coefficientOfFrictionThread * middleDiamThread_m)
                / 2 * (Math.PI - coefficientOfFrictionThread * stepThread_m / middleDiamThread_m))
                + coefficientOfFrictionBoltHead * radFriction_m);
    }

    public double midleDiametr(double diametrThread){
        return diametrThread * 0.96;
    }

    /**
     * сила max по прочности резьбы Fmax = Sigma * П * d1 * h * K * Km
     */
    public int powerMaxByLimitInThread_N(int ultimateStrength_Mpa
            , double diametrThread_mm
            , double middleDiamThread_mm
            , double k_threadDepth
            , double safetyFactor){
    return (int)(ultimateStrength_Mpa * 1000000 * 3.14 * middleDiamThread_mm * 0.001
            * diametrThread_mm * 0.001 * k_threadDepth * safetyFactor * Km * K);
    }
    public int powerMaxForMaterial_kgs(int power_N){
        return (int)(power_N * 0.1);
    }

    /**
     * напряжение в резьбе по силе затяжке
     */
    public int strengthInThread_Mpa(int power_N, double dBolt_mm, double dmidleThread_mm, double k_depth){
        return (int) ((power_N / (3.14 * dmidleThread_mm * 0.001 * dBolt_mm * 0.001 * k_depth * K * Km)) * 0.000001);
    }

    /**
     * напряжение в болте по силе затяжке
     */
    public int strengthInBoltRot_Mpa(int power_N, double dBolt_mm){
        return (int) ((power_N / (3.14 * Math.pow((dBolt_mm * 0.8 * 0.001), 2) * 0.25)) * 0.000001);
    }

    /**
     * сила максимальная по условию прочности болта
     */
    public int powerMaxByLimitInBoltRot_N (int limit_Mpa, double dBolt_mm, double safetyFactor){
        return (int)(limit_Mpa * 1000000 * 3.14 * (Math.pow((dBolt_mm * 0.8 * 0.001), 2) * 0.25) * safetyFactor);
    }



    /**
     * минимальная сила из трех максимально допустимых сил
     */
    public int powerMax_N(int powerMaxBolt, int powerMaxThreadBolt, int powerMaxThreadScrew, String noBolt){

        if (Objects.nonNull(noBolt)) {
            return powerMaxThreadScrew;
        }
        else {

            return Math.min(Math.min(powerMaxBolt, powerMaxThreadBolt), powerMaxThreadScrew);
        }
            }

    /**
     * получение листа моментов из формы HTML
     */
  public List<MomentsEntity> getListMomentByRequestParam (HttpServletRequest request, List<ThreadEntity> thread_FromBD){

                List<MomentsEntity> moments_entities = new ArrayList<>();

        for (ThreadEntity thread: thread_FromBD){

            Double momentParametr = 0.0;

            if (Strings.isNotBlank(request.getParameter(thread.getThread()))){

                momentParametr = Double.parseDouble(request.getParameter(thread.getThread()));
            }

            MomentsEntity moment = MomentsEntity.builder()
                    .momentsNm(momentParametr)
                    .thread(thread)
                    .build();

            moments_entities.add(moment);
             }
                return moments_entities;
            }

    /**
     * получение листа вычисленных моментов по данным материала из формы HTML
     */


    /***
     *Получение листа всех моментов из листа материалов
     */
    public List<MomentsEntity> getListMomentFromListMaterial(List<MaterialsEntity> materals_s){

        List<MomentsEntity>moments_s = new ArrayList<>();

        materals_s.forEach(materal -> {
            moments_s.addAll(materal.getMomentsEntity());
        });
        return moments_s;
    }

    /**
     * Высиление максимально допустимой силы стяжки для пары гайка-болт

     */
    public int powerMaxForBoltSqrew(BoltEntity bolt, SqrewEntity sqrew, ThreadEntity currentThread, Map<String, String> dataFromForm) {


//        try {
//            Double.parseDouble(dataFromForm.get("middleDiamThread_mm"));
//            Double.parseDouble(dataFromForm.get("k_threadDepth"));
//            Double.parseDouble(dataFromForm.get("safetyFactor"));
//        } catch (Exception e) {
//            throw new FormatException("неверный формат данных");
//        }

        int pMaxByThreadBolt_N = powerMaxByLimitInThread_N(
            bolt.getLimit(),
            currentThread.getDBolt_mm(),
            Double.parseDouble(dataFromForm.get("middleDiamThread_mm")),
            Double.parseDouble(dataFromForm.get("k_threadDepth")),
            Double.parseDouble(dataFromForm.get("safetyFactor")));


        int pMaxByThreadSqrew_N = powerMaxByLimitInThread_N(
                sqrew.getLimit(),
                currentThread.getDBolt_mm(),
                Double.parseDouble(dataFromForm.get("middleDiamThread_mm")),
                Double.parseDouble(dataFromForm.get("k_threadDepth")),
                Double.parseDouble(dataFromForm.get("safetyFactor")));

        int pMaxByBoltRot_N = powerMaxByLimitInBoltRot_N(
                    bolt.getLimit(),
                    currentThread.getDBolt_mm(),
                    Double.parseDouble(dataFromForm.get("safetyFactor")));

        return powerMax_N(
            pMaxByBoltRot_N,
            pMaxByThreadBolt_N,
            pMaxByThreadSqrew_N,
            null);

    }

    /**
     * Вычисление крутящего момента по Каллерману для пары гайка-болт с данными из формы
     */
    public double momentsKallermanForBoltSqrew_N(
            BoltEntity bolt,
            SqrewEntity sqrew,
            ThreadEntity thread,
            MaterialsEntity material,
            Map<String, String> dataFromForm) throws FormatException {



        int powerMaxTemp_N = powerMaxForBoltSqrew(
                bolt,
                sqrew,
                thread,
                dataFromForm);

        return momentKellerman_NM(
                powerMaxTemp_N,
                thread.getDMidlethread_mm(),
                thread.getStepThread_mm(),
                material.getCoeffFricThread(),
                material.getCoeffFricBoltHead(),
                thread.getDHead_mm(),
                thread.getDhole_mm());
           }

    /**
     * Вычисление крутящего момента по Каллерману для пары гайка-болт с данными из формы и посчитанной силой
     */
    public double momentsKallermanForBoltSqrew_N(int powerMax_N, ThreadEntity thread, MaterialsEntity material){


        return momentKellerman_NM(
                powerMax_N,
                thread.getDMidlethread_mm(),
                thread.getStepThread_mm(),
                material.getCoeffFricThread(),
                material.getCoeffFricBoltHead(),
                thread.getDHead_mm(),
                thread.getDhole_mm());
    }
}
