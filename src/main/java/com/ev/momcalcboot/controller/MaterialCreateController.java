package com.ev.momcalcboot.controller;

import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.Entity.MomentsEntity;
import com.ev.momcalcboot.Entity.ThreadEntity;
import com.ev.momcalcboot.Entity.UserEntity;
import com.ev.momcalcboot.dao.MaterialsDao;
import com.ev.momcalcboot.dao.MomentsDao;
import com.ev.momcalcboot.dao.ThreadDao;
import com.ev.momcalcboot.dao.UserDao;
import com.ev.momcalcboot.enums.CookiesParametr;
import com.ev.momcalcboot.service.external.ControllerService;
import com.ev.momcalcboot.service.internal.CookService;
import com.ev.momcalcboot.service.internal.MaterialService;
import com.ev.momcalcboot.service.internal.MomentService;
import com.ev.momcalcboot.service.internal.TempObjectService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.var;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Controller
@Data
public class MaterialCreateController {

    private final MaterialsDao materialsDao;

    private final MomentsDao momentsDao;

    private final ThreadDao threadDao;

    private final CookService cookService;

    private final UserDao userDao;

    private final MomentService momentService;

    private final MaterialService materialService;

    private final TempObjectService tempObjectService;

    private final ControllerService controllerService;

    /**
     * Переход к странице создания материала вручную и авто
     */
    @GetMapping("/materials_create")
    public String materialsCreate(Model model, HttpServletRequest request) {

        Cookie cookieUserName = cookService.findCookByName(request, CookiesParametr.USERNAME.getParam());


        if(Objects.nonNull(cookieUserName)) {

            model.addAttribute("userName", cookieUserName.getValue());

            var materialsEntity = new MaterialsEntity();
            List<ThreadEntity> thread_entities = threadDao.getThread();
            var moments_db = new MomentsEntity();

            model.addAttribute("newMaterial", materialsEntity);
            model.addAttribute("threads", thread_entities);
            model.addAttribute("newThreads", moments_db);

            return "materials_create.html";
        }

        return "redirect:/user_registration";

    }

    /**
     * Сохранение материала созданного вручную
     */
    @PostMapping("/materials_create")
    public String materialSave(HttpServletRequest request) {

        /**
         * пользователь из куков
         */
        Cookie cookieUserId = cookService.findCookByName(request, CookiesParametr.USERID.getParam());

        if (Objects.nonNull(cookieUserId)) {
            UserEntity userEntity = userDao.getUserById(Integer.parseInt(cookieUserId.getValue()));
            /**
             * сохр.материала из формы (@ModelAttribute)
             */



            MaterialsEntity materialsEntity = materialService.getMaterialByRequestParam(controllerService.getParametrFromForm(request));

            materialsEntity.setUser(userEntity);

            materialsDao.save(materialsEntity);

            List<ThreadEntity> thread_entities = threadDao.getThread();

            /**
             * получение моментов из формы и сохранение
             */

            List<MomentsEntity> moments_entities = momentService.getListMomentByRequestParam(request, thread_entities);


                for (MomentsEntity moment: moments_entities){

                    moment.setMaterialsEntity(materialsEntity);

                    momentsDao.saveAll(moment);
                }

            return "redirect:/moment_page_1";
        }
        return "redirect:/user_registration";
    }

    /**
     * Переход к созданию материала авто
     */
    @GetMapping("/materials_create_auto")
    public String materialsCreateAuto(Model model, HttpServletRequest request, HttpServletResponse response) {

        model.addAttribute("userName", cookService.findCookByName(request, CookiesParametr.USERNAME.getParam()).getValue());

        MaterialsEntity materialsEntity = MaterialsEntity.builder()
                .materials("Сталь")
                .materialScrew("Сталь")
                .limitStrength(800)
                .limitScrew(700)
                .strengthClass(0.0)
                .classScrew(0.0)
                .safetyFactor(0.75)
                .coeffFricThread(0.16)
                .coeffFricBoltHead(0.16)
                .kDepth(0.8)
                .build();

        model.addAttribute("material", materialsEntity);




//        Cookie materialNameCook = new Cookie("materialName",request.getParameter("material"));
//        response.addCookie(materialNameCook);
//        materialNameCook.setMaxAge(300000);
//
//        Cookie materialLimitCook = new Cookie("materialLimit",request.getParameter("limit"));
//        response.addCookie(materialLimitCook);
//        materialLimitCook.setMaxAge(300000);
//
//        Cookie materialClassCook = new Cookie("materialClass",request.getParameter("class"));
//        materialClassCook.setMaxAge(300000);
//        response.addCookie(materialNameCook);

        return "materials_create_auto.html";
    }


    /**
     * Вычисление моментов при создании материала авто
     */
    @GetMapping("/materials_create_calc")
    public String materialsCreateCalc(Model model, HttpServletRequest request, HttpServletResponse response) {

        model.addAttribute("userName", cookService.findCookByName(request, CookiesParametr.USERNAME.getParam()).getValue());

        MaterialsEntity materals_entity = new MaterialsEntity();
        /**
         * Проверка наличия материала во временном списке, установка материала из временного списка,
         * обнуление временного списка
         */
        if(! tempObjectService.getTempMaterals_entity().isEmpty()){

            materals_entity = tempObjectService.getTempMaterals_entity().get(0);

            tempObjectService.removeTempListMaterial();
        }
        else{
            /**
             * Получение материала из формы HTML
             */


          materals_entity = materialService.getMaterialByRequestParam(controllerService.getParametrFromForm(request));

        }

        List<ThreadEntity> thread_entities = threadDao.getThread();
        List<MomentsEntity> sortedMoment = new ArrayList<>();

        /**
         * Проверка наличия моментов во временном списке, установка моментов из БД или времееного списка,
         * обнуление временного списка
         */
        if(! (tempObjectService.getTempListMoment().isEmpty())){

            List<MomentsEntity> momentsChange = new ArrayList<>();
            momentsChange.addAll(tempObjectService.getTempListMoment());

            for (ThreadEntity thread_entity : thread_entities){
                Predicate<MomentsEntity> filterByThread = (moment) -> {

                    return moment.getThread().getThread().equals(thread_entity.getThread());
                };
                sortedMoment.add(momentsChange.stream().filter(filterByThread).findAny().orElse(null));
            }
            tempObjectService.removeTempListMoment();
        }
        else {
            /**
             * Вычисление моментов по данным материала (из формы HTML)
             */
            List<MomentsEntity> momentsCalc_NM = new ArrayList<>(); // метод удален

            for (ThreadEntity thread_entity : thread_entities){
                Predicate<MomentsEntity> filterByThread = (moment) -> {

                    return moment.getThread().getThread().equals(thread_entity.getThread());
                };
                sortedMoment.add(momentsCalc_NM.stream().filter(filterByThread).findAny().orElse(null));
            }
        }

        model.addAttribute("material", materals_entity);

        model.addAttribute("moments", sortedMoment);

        return  "materials_create_calc.html";

        }

    /**
     * Сохранение созданного материала авто
     */
    @PostMapping("/materials_create_calc")
        public String materialsCreateCalcSave(HttpServletRequest request){

        Cookie cookUserId = cookService.findCookByName(request, CookiesParametr.USERID.getParam());

            if (Objects.nonNull(cookUserId) && Integer.parseInt(cookUserId.getValue()) != 0) {
                int userId = Integer.parseInt(cookUserId.getValue());

                UserEntity userEntity = userDao.getUserById(userId);

                List<ThreadEntity> thread_entities = threadDao.getThread();

                List<MomentsEntity> moments_entities = new ArrayList<>();

                for (ThreadEntity thread_entity : thread_entities){

                    moments_entities.add(MomentsEntity.builder()
                            .momentsNm(Double.parseDouble(request.getParameter(thread_entity.getThread())))
                            .thread(thread_entity)
                            .build());
                                    }
                MaterialsEntity materals_entity = materialService.getMaterialByRequestParam(controllerService.getParametrFromForm(request));
                materals_entity.setUser(userEntity);

                materialsDao.save(materals_entity);

                moments_entities.forEach(moment -> {
                    moment.setMaterialsEntity(materals_entity);
                    momentsDao.saveAll(moment);
                });

                return "redirect:/material_select";
            }

            return "redirect:/user_registration";
        }

    /**
     * Пересчет моментов при авто создании материала, запись в temp
     */
    @PostMapping("/create_recalculate")
    public String materiakUpdateCalc(HttpServletRequest request){

//        int idMaterial = Integer.parseInt(request.getParameter("idMaterial"));

        /**
         * Получение материала из формы
         */
        MaterialsEntity materalsFromHTML = materialService.getMaterialByRequestParam(controllerService.getParametrFromForm(request));
//        materalsFromHTML.setId(idMaterial);

        /**
         * Запись вспомогательного материала(в лист)
         */
        tempObjectService.setParametrForTempListMaterial(materalsFromHTML);

        List<ThreadEntity> thread_entities = threadDao.getThread();

        /**
         * Получение листа вычисленных моментов(по данным материала из формы)
         */
        List<MomentsEntity> momentsCalculate = new ArrayList<>(); // ����� �������� ����� ������

        /**
         * Запись вспомогательного листа моментов
         */
        tempObjectService.setMomentsForTempListMoment(momentsCalculate);

        return "redirect:/materials_create_calc";
    }

}




