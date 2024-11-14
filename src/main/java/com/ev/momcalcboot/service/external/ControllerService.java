package com.ev.momcalcboot.service.external;

import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.Entity.SqrewEntity;
import com.ev.momcalcboot.Entity.ThreadEntity;
import com.ev.momcalcboot.exceptions.FormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ControllerService {

    /**
     *Получение параметров материала, резьбы, болта, гайки из формы ввода
     */
    public Map<String, String> getParametrFromForm(HttpServletRequest request,
                                                   MaterialsEntity material,
                                                   ThreadEntity thread,
                                                   BoltEntity bolt,
                                                   SqrewEntity sqrew) {

        boolean newThread = ! String.valueOf(thread.getId()).equals(request.getParameter("threadIdForm"));

        boolean newSqrew = ! String.valueOf(sqrew.getId()).equals(request.getParameter("sqrewIdForm"));




        Map<String, String> dataForm = new HashMap<>();

        dataForm.put("limateStrengthBolt_Mpa", null);
        dataForm.put("limateStrengthScrew_Mpa", null);
        dataForm.put("diametrThread_mm", null);

        dataForm.put("threadIdForm", null);
        dataForm.put("sqrewIdForm", null);

        dataForm.put("threadName", null);
        dataForm.put("middleDiamThread_mm", null);
        dataForm.put("k_threadDepth", null);
        dataForm.put("safetyFactor", null);
        dataForm.put("powerMaxForMaterial_kgs", null);

        dataForm.put("stepThread_mm", null);
        dataForm.put("coefficientOfFrictionThread", null);
        dataForm.put("coefficientOfFrictionBoltHead", null);
        dataForm.put("diametrHead_mm", null);
        dataForm.put("diametrHole_mm", null);
        dataForm.put("momentKellerman_NM", null);


// прочность болта
        if (Strings.isNotBlank(request.getParameter("limateStrengthBolt_Mpa")) && !newThread) {

            dataForm.put("limateStrengthBolt_Mpa", request.getParameter("limateStrengthBolt_Mpa"));
            try {
                Integer.parseInt(request.getParameter("limateStrengthBolt_Mpa"));
            }
            catch (Exception e){
                throw new FormatException("не верный формат: \"Предел текучести, болт\"");
            }
        }
        else if (Objects.nonNull(bolt.getLimit())){
            dataForm.put("limateStrengthBolt_Mpa", Integer.toString(bolt.getLimit()));
        }

//      Прочность гайки
        if (Strings.isNotBlank(request.getParameter("limateStrengthScrew_Mpa")) && !newThread) {

            dataForm.put("limateStrengthScrew_Mpa", request.getParameter("limateStrengthScrew_Mpa"));
            try {
                Integer.parseInt(request.getParameter("limateStrengthScrew_Mpa"));
            }
            catch (Exception e){
                throw new FormatException("не верный формат: \"Предел текучести, гайка\"");
            }
        }
        else if(Objects.nonNull(sqrew.getLimit())){
            dataForm.put("limateStrengthScrew_Mpa", Integer.toString(sqrew.getLimit()));
        }


// диаметр
        if (Strings.isNotBlank(request.getParameter("diametrThread_mm")) && !newThread) {

            dataForm.put("diametrThread_mm", request.getParameter("diametrThread_mm"));
            try {
                Double.parseDouble(request.getParameter("diametrThread_mm"));
            }
            catch (Exception e){
                throw new FormatException("не верный формат: \"Диаметр гайки\"");
            }
        }
        else if (Objects.nonNull(thread.getDBolt_mm())){
            dataForm.put("diametrThread_mm", Double.toString((thread.getDBolt_mm())));
        }

//      id резьба
        if (Strings.isNotBlank(request.getParameter("threadId")) && !newThread) {

            dataForm.put("threadIdForm", request.getParameter("threadIdForm"));
        }
        else {
            dataForm.put("threadIdForm", Integer.toString(thread.getId()));
        }

//        id гайки
        if (Strings.isNotBlank(request.getParameter("sqrewIdForm")) && !newSqrew) {

            dataForm.put("sqrewIdForm", request.getParameter("sqrewIdForm"));
        }
        else{
             dataForm.put("sqrewIdForm", Integer.toString(sqrew.getId()));
        }

//      Имя резьбы
        if (Strings.isNotBlank(request.getParameter("threadName")) && !newThread) {

            dataForm.put("threadName", request.getParameter("threadName"));
        }
        else if (Objects.nonNull(bolt.getLimit())){
            dataForm.put("threadName", thread.getThread());
        }


//      средний Д резьбы
        if (Strings.isNotBlank(request.getParameter("middleDiamThread_mm")) && !newThread) {

            dataForm.put("middleDiamThread_mm", request.getParameter("middleDiamThread_mm"));
            try {
                Double.parseDouble(request.getParameter("middleDiamThread_mm"));
            }
            catch (Exception e){
                throw new FormatException("не верный формат: \"Средний диаметр резьбы\"");
            }
        }
        else if (Objects.nonNull(thread.getDMidlethread_mm())){
            dataForm.put("middleDiamThread_mm", Double.toString(thread.getDMidlethread_mm()));
        }

//      глубина резьбы
        if (Strings.isNotBlank(request.getParameter("k_threadDepth")) && !newThread && !newSqrew) {

            dataForm.put("k_threadDepth", request.getParameter("k_threadDepth"));
            try {
                Double.parseDouble(request.getParameter("k_threadDepth"));
            }
            catch (Exception e){
                throw new FormatException("не верный формат \"Длина резьбы\"");
            }
        }
        else if (Objects.nonNull(sqrew.getDepth())){
            dataForm.put("k_threadDepth", Double.toString(sqrew.getDepth()));
        }

// коэфф безопасности
        if (Strings.isNotBlank(request.getParameter("safetyFactor")) && !newThread) {
            dataForm.put("safetyFactor", request.getParameter("safetyFactor"));
            try {
                Double.parseDouble(request.getParameter("safetyFactor"));
            }
            catch (Exception e){
                throw new FormatException("не верный формат \"Коэфф. безопасности\"");
            }
        }
        else if (Objects.nonNull(material.getSafetyFactor())){
            dataForm.put("safetyFactor", Double.toString(material.getSafetyFactor()));
        }

//      Шаг резьбы
        if (Strings.isNotBlank(request.getParameter("stepThread_mm")) && !newThread) {
            dataForm.put("stepThread_mm", request.getParameter("stepThread_mm"));
            try {
                Double.parseDouble(request.getParameter("stepThread_mm"));
            }
            catch (Exception e){
                throw new FormatException("не верный формат \"Шаг резьбы\"");
            }
        }
        else if (Objects.nonNull(thread.getStepThread_mm())){
            dataForm.put("stepThread_mm", Double.toString(thread.getStepThread_mm()));
        }


        if (Strings.isNotBlank(request.getParameter("coefficientOfFrictionThread")) && !newThread) {
            dataForm.put("coefficientOfFrictionThread", request.getParameter("coefficientOfFrictionThread"));
            try {
                Double.parseDouble(request.getParameter("coefficientOfFrictionThread"));
            }
            catch (Exception e){
                throw new FormatException("не верный формат \"Коэфф. трения резьбы\"");
            }
        }
        else if (Objects.nonNull(material.getCoeffFricThread())){
            dataForm.put("coefficientOfFrictionThread", Double.toString(material.getCoeffFricThread()));
        }

// коэфф. трения
        if (Strings.isNotBlank(request.getParameter("coefficientOfFrictionBoltHead")) && !newThread) {
            dataForm.put("coefficientOfFrictionBoltHead", request.getParameter("coefficientOfFrictionBoltHead"));
            try {
                Double.parseDouble(request.getParameter("coefficientOfFrictionBoltHead"));
            }
            catch (Exception e){
                throw new FormatException("не верный формат \"Коэфф. трения головки болта\"");
            }
        }
        else if (Objects.nonNull(material.getCoeffFricBoltHead())){
            dataForm.put("coefficientOfFrictionBoltHead", Double.toString(material.getCoeffFricBoltHead()));
        }

// Д головки болта.
        if (Strings.isNotBlank(request.getParameter("diametrHead_mm")) && !newThread) {
            dataForm.put("diametrHead_mm", request.getParameter("diametrHead_mm"));
            try {
                Double.parseDouble(request.getParameter("diametrHead_mm"));
            }
            catch (Exception e){
                throw new FormatException("не верный формат \"диаметр головки болта.\"");
            }
        }
        else if (Objects.nonNull(thread.getDHead_mm())){
            dataForm.put("diametrHead_mm", Double.toString(thread.getDHead_mm()));
        }

//       Д отв.
        if (Strings.isNotBlank(request.getParameter("diametrHole_mm")) && !newThread) {
            dataForm.put("diametrHole_mm", request.getParameter("diametrHole_mm"));
            try {
                Double.parseDouble(request.getParameter("diametrHole_mm"));
            }
            catch (Exception e){
                throw new FormatException("не верный формат \"диаметр отв.\"");
            }
        }
        else if (Objects.nonNull(thread.getDhole_mm())){
            dataForm.put("diametrHole_mm", Double.toString(thread.getDhole_mm()));
        }
    return dataForm;
    }

    /**
     * Получение параметров по умолчанию материала, резьбы, болта, гайки
     */
    public Map<String, String> getParametrFormDefoult(MaterialsEntity material,
                                                      ThreadEntity thread,
                                                      BoltEntity bolt,
                                                      SqrewEntity sqrew){


        Map<String, String> dataFormDefoult = new HashMap<>();

        dataFormDefoult.put("limateStrengthBolt_Mpa", null);
        dataFormDefoult.put("limateStrengthScrew_Mpa", null);
        dataFormDefoult.put("diametrThread_mm", null);
        dataFormDefoult.put("threadIdForm", null);
        dataFormDefoult.put("threadName", null);
        dataFormDefoult.put("middleDiamThread_mm", null);
        dataFormDefoult.put("k_threadDepth", null);
        dataFormDefoult.put("safetyFactor", null);
        dataFormDefoult.put("powerMaxForMaterial_kgs", null);

        dataFormDefoult.put("stepThread_mm", null);
        dataFormDefoult.put("coefficientOfFrictionThread", null);
        dataFormDefoult.put("coefficientOfFrictionBoltHead", null);
        dataFormDefoult.put("diametrHead_mm", null);
        dataFormDefoult.put("diametrHole_mm", null);
        dataFormDefoult.put("momentKellerman_NM", null);



      if (Objects.nonNull(bolt.getLimit())){
            dataFormDefoult.put("limateStrengthBolt_Mpa", Integer.toString(bolt.getLimit()));
        }


       if(Objects.nonNull(sqrew.getLimit())){
            dataFormDefoult.put("limateStrengthScrew_Mpa", Integer.toString(sqrew.getLimit()));
        }


         if (Objects.nonNull(thread.getDBolt_mm())){
            dataFormDefoult.put("diametrThread_mm", Double.toString((thread.getDBolt_mm())));
        }


        if(Objects.nonNull(sqrew.getLimit())){
            dataFormDefoult.put("threadIdForm", Integer.toString(thread.getId()));
        }


        if (Objects.nonNull(bolt.getLimit())){
            dataFormDefoult.put("threadName", thread.getThread());
        }


        if (Objects.nonNull(thread.getDMidlethread_mm())){
            dataFormDefoult.put("middleDiamThread_mm", Double.toString(thread.getDMidlethread_mm()));
        }


        if (Objects.nonNull(sqrew.getDepth())){
            dataFormDefoult.put("k_threadDepth", Double.toString(sqrew.getDepth()));
        }


        if (Objects.nonNull(material.getSafetyFactor())){
            dataFormDefoult.put("safetyFactor", Double.toString(material.getSafetyFactor()));
        }


        if (Objects.nonNull(thread.getStepThread_mm())){
            dataFormDefoult.put("stepThread_mm", Double.toString(thread.getStepThread_mm()));
        }


        if (Objects.nonNull(material.getCoeffFricThread())){
            dataFormDefoult.put("coefficientOfFrictionThread", Double.toString(material.getCoeffFricThread()));
        }


        if (Objects.nonNull(material.getCoeffFricBoltHead())){
            dataFormDefoult.put("coefficientOfFrictionBoltHead", Double.toString(material.getCoeffFricBoltHead()));
        }


        if (Objects.nonNull(thread.getDHead_mm())){
            dataFormDefoult.put("diametrHead_mm", Double.toString(thread.getDHead_mm()));
        }

        if (Objects.nonNull(thread.getDhole_mm())){
            dataFormDefoult.put("diametrHole_mm", Double.toString(thread.getDhole_mm()));
        }
        return dataFormDefoult;

    }

//    HashMap<String, HashMap> selects = new HashMap<String, HashMap>();
//for(Map.Entry<String, HashMap> entry : selects.entrySet()) {
//        String key = entry.getKey();
//        HashMap value = entry.getValue();


    /**
    * Установка атрибутов Class для формы ввода при сравнении с дефолтными значениями
     */
    public Map<String, String> setClassAtributeForForm(Map<String, String> mapDefoult, Map<String, String> mapChange){

        Map<String, String> classDataForm = new HashMap<>();

        classDataForm.put("middleDiamThread_mm", "btn btn-outline-dark");
        classDataForm.put("k_threadDepth", "btn btn-outline-dark");
        classDataForm.put("safetyFactor", "btn btn-outline-dark");


        classDataForm.put("stepThread_mm", "btn btn-outline-dark");
        classDataForm.put("coefficientOfFrictionThread", "btn btn-outline-dark");
        classDataForm.put("coefficientOfFrictionBoltHead", "btn btn-outline-dark");
        classDataForm.put("diametrHead_mm", "btn btn-outline-dark");
        classDataForm.put("diametrHole_mm", "btn btn-outline-dark");


            mapDefoult.forEach((key, val) -> {
               String valueChange =  mapChange.entrySet().stream()
                        .filter(elem -> elem.getKey().equals(key)).findAny()
                       .map(Map.Entry::getValue)
                       .orElse(null);
                    if (valueChange != null && ! valueChange.equals(val)){
                            classDataForm.put(key, "btn btn-secondary");
                    }
            } );
            return classDataForm;

    }



    public Map<String, String> getParametrFromForm(HttpServletRequest request){





        Map<String, String> dataForm = new HashMap<>();

        dataForm.put("limateStrengthBolt_Mpa", null);
        dataForm.put("limateStrengthScrew_Mpa", null);
        dataForm.put("diametrThread_mm", null);
        dataForm.put("threadIdForm", null);
        dataForm.put("threadName", null);
        dataForm.put("middleDiamThread_mm", null);
        dataForm.put("k_threadDepth", null);
        dataForm.put("safetyFactor", null);
        dataForm.put("powerMaxForMaterial_kgs", null);

        dataForm.put("stepThread_mm", null);
        dataForm.put("coefficientOfFrictionThread", null);
        dataForm.put("coefficientOfFrictionBoltHead", null);
        dataForm.put("diametrHead_mm", null);
        dataForm.put("diametrHole_mm", null);
        dataForm.put("momentKellerman_NM", null);

        /**
         * установка значений  из формы
         */

        if (Strings.isNotBlank(request.getParameter("limateStrengthBolt_Mpa"))) {

            dataForm.put("limateStrengthBolt_Mpa", request.getParameter("limateStrengthBolt_Mpa"));
        }
        else {
            dataForm.put("limateStrengthBolt_Mpa", "0");
        }


        if (Strings.isNotBlank(request.getParameter("limateStrengthScrew_Mpa"))) {

            dataForm.put("limateStrengthScrew_Mpa", request.getParameter("limateStrengthScrew_Mpa"));
        }
        else{
            dataForm.put("limateStrengthScrew_Mpa", "0");
        }



        if (Strings.isNotBlank(request.getParameter("diametrThread_mm"))) {

            dataForm.put("diametrThread_mm", request.getParameter("diametrThread_mm"));
        }
        else {
            dataForm.put("diametrThread_mm", "0");
        }


        if (Strings.isNotBlank(request.getParameter("threadId"))){

            dataForm.put("threadIdForm", request.getParameter("threadIdForm"));
        }
        else {
            dataForm.put("threadIdForm", "0");
        }


        if (Strings.isNotBlank(request.getParameter("threadName"))){

            dataForm.put("threadName", request.getParameter("threadName"));
        }
        else {
            dataForm.put("threadName", "0");
        }

        if (Strings.isNotBlank(request.getParameter("middleDiamThread_mm"))) {

            dataForm.put("middleDiamThread_mm", request.getParameter("middleDiamThread_mm"));
        }
        else{
            dataForm.put("middleDiamThread_mm", "0");
        }


        if (Strings.isNotBlank(request.getParameter("k_threadDepth"))) {

            dataForm.put("k_threadDepth", request.getParameter("k_threadDepth"));
        }
        else {
            dataForm.put("k_threadDepth","0");
        }


        if (Strings.isNotBlank(request.getParameter("safetyFactor"))){
            dataForm.put("safetyFactor", request.getParameter("safetyFactor"));
        }
        else {
            dataForm.put("safetyFactor", "0");
        }


        if (Strings.isNotBlank(request.getParameter("stepThread_mm"))) {
            dataForm.put("stepThread_mm", request.getParameter("stepThread_mm"));
        }
        else{
            dataForm.put("stepThread_mm", "0");
        }


        if (Strings.isNotBlank(request.getParameter("coefficientOfFrictionThread"))) {
            dataForm.put("coefficientOfFrictionThread", request.getParameter("coefficientOfFrictionThread"));
        }
        else{
            dataForm.put("coefficientOfFrictionThread", "0.14");
        }


        if (Strings.isNotBlank(request.getParameter("coefficientOfFrictionBoltHead"))) {
            dataForm.put("coefficientOfFrictionBoltHead", request.getParameter("coefficientOfFrictionBoltHead"));
        }
        else{
            dataForm.put("coefficientOfFrictionBoltHead", "0.14");
        }


        if (Strings.isNotBlank(request.getParameter("diametrHead_mm"))) {
            dataForm.put("diametrHead_mm", request.getParameter("diametrHead_mm"));
        }
        else{
            dataForm.put("diametrHead_mm", "0");
        }

        if (Strings.isNotBlank(request.getParameter("diametrHole_mm"))) {
            dataForm.put("diametrHole_mm", request.getParameter("diametrHole_mm"));
        }
        else{
            dataForm.put("diametrHole_mm","0");
        }
        return dataForm;
    }


}
