package com.ev.momcalcboot.controller;

import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.Entity.MomentsEntity;
import com.ev.momcalcboot.Entity.ThreadEntity;
import com.ev.momcalcboot.dao.MaterialsDao;
import com.ev.momcalcboot.dao.MomentsDao;
import com.ev.momcalcboot.dao.ThreadDao;
import com.ev.momcalcboot.service.external.ControllerService;
import com.ev.momcalcboot.service.internal.MaterialService;
import com.ev.momcalcboot.service.internal.MomentService;
import com.ev.momcalcboot.service.internal.TempObjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


@Controller
public class ControllerMaterialsUpdate {



    @Autowired
    private ThreadDao threadDao;

    @Autowired
    private MomentsDao momentsDao;

    @Autowired
    private MaterialsDao materialsDao;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MomentService momentService;

    @Autowired
    private TempObjectService tempObjectService;

    private ControllerService controllerService;




    @GetMapping("/materials_update/{id}")
    public String update(Model model, HttpServletRequest request, @PathVariable("id") int id){

//        tempObjectService.getTempMaterals_db().get(0);


        MaterialsEntity materalsEntity = materialsDao.getMaterialsById(id);

        List<ThreadEntity> threadEntities = threadDao.getThread();

        List<MomentsEntity> momentsEntity = materalsEntity.getMomentsEntity();

        List<MomentsEntity> sortedMoment = new ArrayList<>();

        /**
         * Проверка наличия материала во временном списке, установка материала из временного списка,
         * обнуление временного списка
         */
         if(! tempObjectService.getTempMaterals_entity().isEmpty()){

             materalsEntity = tempObjectService.getTempMaterals_entity().get(0);

             tempObjectService.removeTempListMaterial();
         }


        /**
         * Проверка наличия моментов во временном списке, установка моментов из БД или времееного списка,
         * обнуление временного списка
         */

        if(! (tempObjectService.getTempListMoment().isEmpty())){

            List<MomentsEntity> momentsChange = new ArrayList<>();
            momentsChange.addAll(tempObjectService.getTempListMoment());

            for (ThreadEntity thread_entity : threadEntities){
                Predicate<MomentsEntity> filterByThread = (moment) -> {
                    System.out.println("predicate 9999999");
                    return moment.getThread().getThread().equals(thread_entity.getThread());
                };
                sortedMoment.add(momentsChange.stream().filter(filterByThread).findAny().orElse(null));
            }
            tempObjectService.removeTempListMoment();
        }
        else {
            for (ThreadEntity thread_entity : threadEntities){
                Predicate<MomentsEntity> filterByThread = (moment) -> {
                    System.out.println("predicate 9999999");
                    return moment.getThread().getThread().equals(thread_entity.getThread());
                };
                sortedMoment.add(momentsEntity.stream().filter(filterByThread).findAny().orElse(null));
            }
        }

        model.addAttribute("material", materalsEntity);
        model.addAttribute("theads", threadEntities);
        model.addAttribute("moments", sortedMoment);

        return "update_material.html";

    }
    @PostMapping("/update/{id}")
    public String saveApdate(@PathVariable("id") int id
            , HttpServletRequest request){


        MaterialsEntity materal_dbForUpdate = materialsDao.getMaterialsById(id);   // материал по id из БД

        List<MomentsEntity> moments_entityForUpdate = materal_dbForUpdate.getMomentsEntity();  //моменты из материала


        List<ThreadEntity> thread_entities = threadDao.getThread(); //резьба из БД

        thread_entities.forEach(thread -> {
           MomentsEntity momentForUpdate = moments_entityForUpdate.stream()
                   .filter(moment -> moment.getThread().getThread().equals(thread.getThread())).findAny().orElse(null); // поиск момента по резьбе
            momentForUpdate.setMomentsNm(Double.valueOf(request.getParameter(thread.getThread()))); // установка значений моментов из формы
            momentsDao.updateMoment(momentForUpdate); // сохранение момента
        });
        /**
         * получение материала из формы
         */
        MaterialsEntity materalForm = materialService.getMaterialByRequestParam(controllerService.getParametrFromForm(request));

        /**
         * установка новых значений для материала
         */
        materal_dbForUpdate.setMaterials(materalForm.getMaterials());
        materal_dbForUpdate.setStrengthClass(materalForm.getStrengthClass());
        materal_dbForUpdate.setLimitStrength(materalForm.getLimitStrength());

        materal_dbForUpdate.setMaterialScrew(materalForm.getMaterialScrew());
        materal_dbForUpdate.setClassScrew(materalForm.getClassScrew());
        materal_dbForUpdate.setLimitScrew(materalForm.getLimitScrew());

        materal_dbForUpdate.setComments(materalForm.getComments());
        materal_dbForUpdate.setSafetyFactor(materalForm.getSafetyFactor());
        materal_dbForUpdate.setKDepth(materalForm.getKDepth());

        materal_dbForUpdate.setCoeffFricBoltHead(materalForm.getCoeffFricBoltHead());
        materal_dbForUpdate.setCoeffFricThread(materalForm.getCoeffFricThread());

        materal_dbForUpdate.setMomentsEntity(moments_entityForUpdate);

        materialsDao.updateMaterial(materal_dbForUpdate);

//        return "redirect:/moment_page_1";
        return "redirect:/materials_update/" + materal_dbForUpdate.getId();
    }

    @PostMapping("/update_calc")
    public String materiakUpdateCalc(HttpServletRequest request){

        int idMaterial = Integer.parseInt(request.getParameter("idMaterial"));

        /**
         * Получение материала из формы
         */
        MaterialsEntity materalsFromHTML = materialService.getMaterialByRequestParam(controllerService.getParametrFromForm(request));
        materalsFromHTML.setId(idMaterial);

        /**
         * Запись вспомогательного материала(в лист)
         */
        tempObjectService.setParametrForTempListMaterial(materalsFromHTML);

        List<ThreadEntity> thread_entities = threadDao.getThread();

        /**
         * Получение листа вычисленных моментов(по данныь материала из формы)
         */
//        List<Moments_db> listMomentFromHTML = momentService.getListMomentByRequestParam(request, thread_dbs);

        List<MomentsEntity> momentsCalculate = new ArrayList<>(); //метод удален

        /**
         * Запись вспомогательного листа моментов
         */
        tempObjectService.setMomentsForTempListMoment(momentsCalculate);

        return "redirect:/materials_update/" + idMaterial;
    }

}
