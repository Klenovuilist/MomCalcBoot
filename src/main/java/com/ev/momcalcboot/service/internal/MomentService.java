package com.ev.momcalcboot.service.internal;

import com.ev.momcalcboot.Entity.*;
import com.ev.momcalcboot.enums.koeff.*;
import com.ev.momcalcboot.exceptions.FormatException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.ev.momcalcboot.service.internal.ParserNumber.toDouble;


@Data
@Service
@RequiredArgsConstructor

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
     * Расчет максимальной допустипой силы по условию прочности резьбы
     * нагрузка на резьбу равномерная по глубине с учетом коэфф.
     * Fmax = Sigma * ПИ * d1 * h * Kбез. * Km
     */
    public int powerMaxByLimitInThread_N(int ultimateStrength_Mpa
            , double diametrThread_mm
            , double middleDiamThread_mm
            , double k_threadDepth
            , double safetyFactor){
    return (int)(ultimateStrength_Mpa * 1000000 * 3.14 * middleDiamThread_mm * 0.001
            * diametrThread_mm * 0.001 * k_threadDepth * safetyFactor * Km * K);
    }

    /**
     * Расчет максимальной допустипой силы по условию прочности
     * первого витка резьбы
     * Fmax = Sigma * ПИ * d1 * шаг * Kбез. * Km / koff. нагрузки
     */
    public int powerMaxByLimitOnOneTrust_N(
              double dBolt_mm
            , double middleDiamThread_mm
            , double stepThread_mm
            , int ultimateStrength_Mpa
            , double safetyFactor
            , double k_threadDepth){

        int countTurn = countWorkTurn(dBolt_mm, k_threadDepth, stepThread_mm);

        double k_power = 1;

                    switch (countTurn){
                        case 1:
                            k_power = 1;
                            break;
                        case 2:
                            k_power = StrengthInThread_2.ONE_TURN.getKoeff();
                            break;
                        case 3:
                            k_power = StrengthInThread_3.ONE_TURN.getKoeff();
                            break;
                        case 4:
                            k_power = StrengthInThread_4.ONE_TURN.getKoeff();
                            break;
                        case 5:
                            k_power = StrengthInThread_5.ONE_TURN.getKoeff();
                            break;
                        case 6:
                            k_power = StrengthInThread_6.ONE_TURN.getKoeff();
                            break;
                        case 7:
                            k_power = StrengthInThread_7.ONE_TURN.getKoeff();
                            break;
                        case 8:
                            k_power = StrengthInThread_8.ONE_TURN.getKoeff();
                            break;
                        case 9:
                            k_power = StrengthInThread_9.ONE_TURN.getKoeff();
                            break;
                        default:
                            k_power = StrengthInThread_10.ONE_TURN.getKoeff();
                            break;
                                                }

        return (int)((ultimateStrength_Mpa * 1000000 * 3.14 * middleDiamThread_mm * 0.001
                * stepThread_mm * 0.001 * safetyFactor * K) / k_power);
    }

        /**
         *перевод силы из Н в кгс
         */
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
     * напряжение в в стержне болта по силе затяжке
     */
    public int strengthInBoltRot_Mpa(int power_N, double dBolt_mm){
        return (int) ((power_N / (3.14 * Math.pow((dBolt_mm * 0.8 * 0.001), 2) * 0.25)) * 0.000001);
    }

    /**
     * сила максимальная по условию прочности болта стержня болта
     */
    public int powerMaxByLimitInBoltRot_N (int limit_Mpa, double dBolt_mm, double safetyFactor){
        return (int)(limit_Mpa * 1000000 * 3.14 * (Math.pow((dBolt_mm * 0.8 * 0.001), 2) * 0.25) * safetyFactor);
    }



    /**
     * получение минимальной силы из трех максимально допустимых сил
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

                momentParametr = toDouble(request.getParameter(thread.getThread()));
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
     * по учловию прочности либо стержня болта либо прочности на первом витке

     */

    public List<Integer> powerMaxForBoltSqrewList(BoltEntity bolt, SqrewEntity sqrew, ThreadEntity currentThread, Map<String, String> dataFromForm) {
            List<Integer> power = new ArrayList<>();
// заменен на pMaxByOneTurnBolt
//        int pMaxByThreadBolt_N = powerMaxByLimitInThread_N(
//            bolt.getLimit(),
//            currentThread.getDBolt_mm(),
//            Double.parseDouble(dataFromForm.get("middleDiamThread_mm")),
//            Double.parseDouble(dataFromForm.get("k_threadDepth")),
//            Double.parseDouble(dataFromForm.get("safetyFactor")));


        int pMaxByOneTurnBolt_N = powerMaxByLimitOnOneTrust_N(
        currentThread.getDBolt_mm(),
        toDouble(dataFromForm.get("middleDiamThread_mm")),
        toDouble(dataFromForm.get("stepThread_mm")),
        bolt.getLimit(),
        toDouble(dataFromForm.get("safetyFactor")),
        toDouble(dataFromForm.get("k_threadDepth")));

// Заменен
//        int pMaxByThreadSqrew_N = powerMaxByLimitInThread_N(
//                sqrew.getLimit(),
//                currentThread.getDBolt_mm(),
//                Double.parseDouble(dataFromForm.get("middleDiamThread_mm")),
//                Double.parseDouble(dataFromForm.get("k_threadDepth")),
//                Double.parseDouble(dataFromForm.get("safetyFactor")));


        int pMaxByOneTurnSqrew_N = powerMaxByLimitOnOneTrust_N(
                currentThread.getDBolt_mm(),
                toDouble(dataFromForm.get("middleDiamThread_mm")),
                toDouble(dataFromForm.get("stepThread_mm")),
                sqrew.getLimit(),
                toDouble(dataFromForm.get("safetyFactor")),
                toDouble(dataFromForm.get("k_threadDepth")));

        int pMaxByBoltRot_N = powerMaxByLimitInBoltRot_N(
                    bolt.getLimit(),
                    currentThread.getDBolt_mm(),
                    toDouble(dataFromForm.get("safetyFactor")));
        power.add(powerMax_N(
                pMaxByBoltRot_N,
                pMaxByOneTurnBolt_N,
                pMaxByOneTurnSqrew_N,
                null));
        power.add(pMaxByBoltRot_N);
        power.add(pMaxByOneTurnBolt_N);
        power.add(pMaxByOneTurnSqrew_N);

        return power;

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

        int powerMaxTemp_N = powerMaxForBoltSqrewList(
                bolt,
                sqrew,
                thread,
                dataFromForm).get(0);

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



    /**
     * Лист с напряжениями на витках резьбы гайки в зависимости от числа витков
     */
    public List<String> strengthInTurnList_Mpa (
            double power_N
            , ThreadEntity thread
            , double stepThread_mm
            , double dMidlethread_mm
            , double k_threadDepth){

        //    количество рабочих витков
        int countWorkTurn = countWorkTurn(
                thread.getDBolt_mm(),
        k_threadDepth,
        stepThread_mm);

        List<Integer> resultList = new ArrayList<>();

        // нагрузка на один виток
        int strengthOnOneTurn_Mpa = (int) ((power_N) / (Math.PI * dMidlethread_mm * 0.001
                * K
                * stepThread_mm * 0.001) * 0.000001);


        switch (countWorkTurn){
            case 1:
                resultList.add(strengthOnOneTurn_Mpa);
                break;
            case 2:
                for (int i = 0; i < countWorkTurn; i++ ){
                    StrengthInThread_2[] enums = StrengthInThread_2.values();
                    // добавление в лист напряжений витков согласно коэфф. нагрузки по виткам
                    resultList.add((int)(strengthOnOneTurn_Mpa * enums[i].getKoeff()));

                }
                break;
            case 3:
                for (int i = 0; i < countWorkTurn; i++ ){
                    StrengthInThread_3[] enums = StrengthInThread_3.values();
                    // добавление в лист напряжений витков согласно коэфф. нагрузки по виткам
                    resultList.add((int)(strengthOnOneTurn_Mpa * enums[i].getKoeff()));
                }
                break;
            case 4:
                for (int i = 0; i < countWorkTurn; i++ ){
                    StrengthInThread_4[] enums = StrengthInThread_4.values();
                    // добавление в лист напряжений витков согласно коэфф. нагрузки по виткам
                    resultList.add((int)(strengthOnOneTurn_Mpa * enums[i].getKoeff()));
                }
                break;
            case 5:
                for (int i = 0; i < countWorkTurn; i++ ){
                    StrengthInThread_5[] enums = StrengthInThread_5.values();
                    // добавление в лист напряжений витков согласно коэфф. нагрузки по виткам
                    resultList.add((int)(strengthOnOneTurn_Mpa * enums[i].getKoeff()));
                }
                break;
            case 6:
                for (int i = 0; i < countWorkTurn; i++ ){
                    StrengthInThread_6[] enums = StrengthInThread_6.values();
                    // добавление в лист напряжений витков согласно коэфф. нагрузки по виткам
                    resultList.add((int)(strengthOnOneTurn_Mpa * enums[i].getKoeff()));
                }
                break;
            case 7:
                for (int i = 0; i < countWorkTurn; i++ ){
                    StrengthInThread_7[] enums = StrengthInThread_7.values();
                    // добавление в лист напряжений витков согласно коэфф. нагрузки по виткам
                    resultList.add((int)(strengthOnOneTurn_Mpa * enums[i].getKoeff()));
                }
                break;
            case 8:
                for (int i = 0; i < countWorkTurn; i++ ){
                    StrengthInThread_8[] enums = StrengthInThread_8.values();
                    // добавление в лист напряжений витков согласно коэфф. нагрузки по виткам
                    resultList.add((int)(strengthOnOneTurn_Mpa * enums[i].getKoeff()));
                }
                break;
            case 9:
                for (int i = 0; i < countWorkTurn; i++ ){
                    StrengthInThread_9[] enums = StrengthInThread_9.values();
                    // добавление в лист напряжений витков согласно коэфф. нагрузки по виткам
                    resultList.add((int)(strengthOnOneTurn_Mpa * enums[i].getKoeff()));
                }
                break;
            default:
                for (int i = 0; i < countWorkTurn; i++ ){
                    StrengthInThread_10[] enums = StrengthInThread_10.values();
                    // добавление в лист напряжений витков согласно коэфф. нагрузки по виткам
                    resultList.add((int)(strengthOnOneTurn_Mpa * enums[i].getKoeff()));
                }
                break;
                }
                List<String>resultString = new ArrayList<>();

                for (int i = 0; i < resultList.size(); i++){
                    resultString.add((i + 1)+ ".   " + resultList.get(i).toString() + " MПa");
                }

        return resultString;
    }

    /**
     * Количество рабочих витков
     */
    public int countWorkTurn (
              double dBolt_mm
            , double k_threadDepth
            , double stepThread_mm){

        int count = (int) (dBolt_mm * k_threadDepth / stepThread_mm);

        if (count >= 10){
            return 10;
        } else if (count >= 1) {
            return count;
        }
        else {
            return 1;
        }
    }

    /**
     * Высота гайки
     * */
    public double hSqrew_mm(ThreadEntity thread, double k_threadDepth){
        return thread.getDBolt_mm() * k_threadDepth;
    }

}
