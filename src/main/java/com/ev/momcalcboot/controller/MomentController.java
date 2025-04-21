package com.ev.momcalcboot.controller;

import com.ev.momcalcboot.Entity.*;
import com.ev.momcalcboot.dao.MaterialsDao;
import com.ev.momcalcboot.dao.ThreadDao;
import com.ev.momcalcboot.enums.AttributeName;
import com.ev.momcalcboot.enums.CookiesParametr;
import com.ev.momcalcboot.exceptions.FormatException;
import com.ev.momcalcboot.service.external.ControllerService;
import com.ev.momcalcboot.service.internal.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static com.ev.momcalcboot.service.internal.ParserNumber.toDouble;
import static com.ev.momcalcboot.service.internal.ParserNumber.toInt;


@Controller
//@AllArgsConstructor
@RequiredArgsConstructor
//@RequestMapping("/")
public class MomentController {


    private final MaterialsDao materialsDao;

    @Autowired
    private ThreadDao threadDao;

    @Autowired
    private MomentService momentService;

    @Autowired
    private BoltService boltService;

    @Autowired
    private SqrewService sqrewService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private ControllerService controllerService;

    @GetMapping("/moment_page_0")
    public String momentPage0() {


        return "moment_page_0.html";
    }

    /**
     * Загрузка главной страницы расчетов по параметрам(copy)
     */
//    @GetMapping("/moment_page_1") //moment_page_1
//    public String moment_page_1(@RequestParam(value = "threadId", required = false) Integer threadId
//            , Model model, HttpServletRequest request, HttpServletResponse response) {  // модель для отображения во view
//        System.out.println("@GetMapping(/moment_page_1");
//
//        int userId = 0;
//
//        Cookie[] cookies = request.getCookies();
//
//        /**
//         * Получение пользователя из куков для модели
//         */
//
//        model.addAttribute(AttributeName.USERNAME.getParam(),"Enter");
//                if(Objects.nonNull(cookies)) {
//                    for (Cookie cookie : cookies) {
//                        if (cookie.getName().equals(CookiesParametr.USERNAME.getParam())) {
//
//                            model.addAttribute("userName", cookie.getValue());
//                        }
//                        if (cookie.getName().equals(CookiesParametr.USERID.getParam())) {
//
//                            userId = Integer.parseInt(cookie.getValue());
//                        }
//
//                    }
//                }
//        List<MaterialsEntity> usersMaterial = null;
//
//        List<MaterialsEntity> adminMaterials = materialsDao.getMaterialsByUserId(1);
//
//        List<MaterialsEntity> allMaterialsEntities = new ArrayList<>(adminMaterials);
//
//        /**
//         * добавление в полный список материалов материлов от юзера
//         */
//        if (userId > 1) {
//
//          usersMaterial = new ArrayList<>(materialsDao.getMaterialsByUserId(userId));
//
//            allMaterialsEntities.addAll(usersMaterial);
//        }
//
//        /**
//         * Получение листа материалов выбранных в форме select(при загрузке страницы_1
//         * из страницы селект)
//         */
//        List<MaterialsEntity> materialsEntities = allMaterialsEntities.stream()
//                .filter(m -> String.valueOf(m.getId()).equals(request.getParameter((String.valueOf(m.getId())))))
//                .collect(Collectors.toList());
//
//        /**
//         * Проверка списка материалов на Empty, если пустой -> заполнить по данным от cookies(если есть cooki)
//         * если кук нет заполнить по списку материалов из БД(если материалы есть в базе)
//         *
//         */
//        if (materialsEntities.isEmpty()) {
//
//            if (cookies != null) {
//                for (Cookie cook : cookies) {
//
//                    materialsEntities.addAll(allMaterialsEntities.stream()
//                            .filter(f -> (CookiesParametr.ID.getParam() + String.valueOf(f.getId())).equals(cook.getName())
//                                    && cook.getValue().equals("1")).collect(Collectors.toList()));
//                }
//            }
//        }
//        if (materialsEntities.isEmpty() && allMaterialsEntities.size() > 0) {
//            for (int i = 0; i < allMaterialsEntities.size(); i++) {
//                materialsEntities.add(allMaterialsEntities.get(i));
//                if (i > 3) break;
//            }
//        }
//
//
//        model.addAttribute("materials", materialsEntities);
//
//        /**
//         *получение моментов и резьб из БД - переделать моменты
//         */
////        List<Moments_db> moments_db = moments_db_dao.getMoments_db();
//
//        List<ThreadEntity> threadEntityNoSorted = threadDao.getThread();
//
//        /**
//         * to do
//         * сортировка листа по возрастанию
//         */
//        List<ThreadEntity> threadEntitySorted = threadEntityNoSorted.stream()
//                .sorted((thread1, thread2) -> thread1.getThread().compareTo(thread2.getThread()))
//                .collect(Collectors.toList());
//
//        model.addAttribute("threads", threadEntitySorted);
///**
// * фильтр списка moments_dbsFilter по значениЮ id Thread, если занчения Thread не пришло
// * взять значение из куков или...
// * установить threadCurrent
// */
//
//
//
//
//
//        /**
//         * Установка текущего значения резьбы threadCurrent:
//         * или  = threadEntitySorted.get(0)
//         * или по threadId
//         * или по cookies
//         */
//        ThreadEntity threadCurrent = threadEntitySorted.get(0);
//
//
//        if (threadId != null) {
//
////            moments_dbsFilter = moments_db.stream()
////                    .filter(moment -> moment.getThread().getId() == threadId).toList();
//
//            threadCurrent = threadEntitySorted.stream()
//                    .filter(thread -> thread.getId() == threadId).findAny().orElse(threadEntitySorted.get(0));
//
//            model.addAttribute("thread", threadCurrent.getThread());//для вспомогательной переменной отображения выбора резьбы
//
//
//        }
//        if(Objects.nonNull(cookies) && threadId == null) {
//
//            Cookie cookieThread = Arrays.stream(cookies)
//                    .filter(f -> f.getName().equals(CookiesParametr.FREADID.getParam())).findAny().orElse(null);
//
//            if (Objects.nonNull(cookieThread)) {
////                moments_dbsFilter = moments_db.stream()
////                        .filter(f -> String.valueOf(f.getThread().getId()).equals(cookieThread.getValue())).toList();
//
//                threadCurrent = threadEntitySorted.stream().filter(thread_db -> String.valueOf(thread_db.getId()).equals(cookieThread.getValue()))
//                        .findAny().orElse(threadEntitySorted.get(0));
//
//                model.addAttribute("thread", threadCurrent.getThread());
//
//            }
//        }
////
////        if (moments_dbsFilter.isEmpty()){
////            moments_dbsFilter = moments_db.stream()
////                    .filter(moment -> moment.getThread().getId() == threadEntitySorted.get(0).getId()).toList();
////
////            model.addAttribute("thread", threadCurrent.getThread());
////        }
//        model.addAttribute("thread", threadCurrent.getThread());
//
//        /**
//         * Создание  списка моментов по соответствию с id материала
//         */
//        List<MomentsEntity> momentsFromMaterials = momentService.getListMomentFromListMaterial(materialsEntities);
//            List<MomentsEntity> momentsSortedByThreadId = new ArrayList<>();
//
//        /**
//         * Сортирока моментов по соответствию с id threadCurrent
//         */
//                ThreadEntity finalThreadCurrent = threadCurrent; //вспомогательная для лямбды
//                momentsSortedByThreadId = momentsFromMaterials.stream()
//                        .filter(moment -> moment.getThread().getId() == finalThreadCurrent.getId()).collect(Collectors.toList());
//
////
////
////            for (Materals_db mat : materals_dbs) {
////                momentsSortedByThreadId.add(momentsFromMaterials.stream()
////                        .filter(moment -> moment.getMaterals_db().getId() == mat.getId()).findAny().orElse(null));
////            }
//            model.addAttribute("moments", momentsSortedByThreadId);
//
//            /**
//             * Вычисление теоретических значений для таблицы материалов:
//             * - силы затяжки
//             * -момента затяжки
//             * для листов материала
//             * И создание листов вычисленных значений
//             */
//            List<Integer> powerByMomentForTable_kgs = new ArrayList<>();
//            List<Integer> stregthByPowerInThreadForTable_MPa = new ArrayList<>();
//            List<Integer> stregthByPowerInBoltForTable_MPa = new ArrayList<>();
//
//            if (! materialsEntities.isEmpty()) {
//                for (MomentsEntity moment : momentsSortedByThreadId) {
//
//                    Double coeffFricThread = moment.getMaterialsEntity().getCoeffFricThread() == null ? 0.14 : moment.getMaterialsEntity().getCoeffFricThread();
//                    Double coeffFricBoltHead = moment.getMaterialsEntity().getCoeffFricBoltHead() == null ? 0.14 : moment.getMaterialsEntity().getCoeffFricBoltHead();
//
//                    int powerKallerman_N = momentService.powerByKallerman_N(
//                            moment.getMomentsNm(),
//                            moment.getThread().getDMidlethread_mm(),
//                            moment.getThread().getStepThread_mm(),
//                            coeffFricThread,
//                            coeffFricBoltHead,
//                            moment.getThread().getDHead_mm(),
//                            moment.getThread().getDhole_mm());
//
//                    powerByMomentForTable_kgs.add((int)(powerKallerman_N * 0.1));
//
//                    Double kDepth = moment.getMaterialsEntity().getKDepth() == null ? 0.8 : moment.getMaterialsEntity().getKDepth();
//
//                    stregthByPowerInThreadForTable_MPa.add(momentService.strengthInThread_Mpa(
//                            powerKallerman_N
//                            , moment.getThread().getDBolt_mm()
//                            , moment.getThread().getDMidlethread_mm()
//                            , kDepth));
//
//                    stregthByPowerInBoltForTable_MPa.add(momentService.strengthInBoltRot_Mpa(
//                            powerKallerman_N
//                            , (moment.getThread().getDBolt_mm())));
//                }
//            }
//            model.addAttribute("powerByMomentTheor_kgs", powerByMomentForTable_kgs);
//            model.addAttribute("stregthByPowerTheor_MPa", stregthByPowerInThreadForTable_MPa);
//            model.addAttribute("stregthByPowerInBoltForTable_MPa", stregthByPowerInBoltForTable_MPa);
//
//            /**
//             * форма вычисления момента и силы
//             */
//            Map<String, String> dataForCalc = new HashMap<>();
//
//        dataForCalc.put("limateStrengthBolt_Mpa", "800");
//        dataForCalc.put("limateStrengthScrew_Mpa", "700");
//        dataForCalc.put("diametrThread_mm", String.valueOf(threadCurrent.getDBolt_mm()));
//        dataForCalc.put("middleDiamThread_mm", String.valueOf(threadCurrent.getDMidlethread_mm()));
//        dataForCalc.put("k_threadDepth", "0.8");
//        dataForCalc.put("safetyFactor", "0.75");
//        dataForCalc.put("powerMaxForMaterial_kgs", "0");
//
//        dataForCalc.put("stepThread_mm", String.valueOf(threadCurrent.getStepThread_mm()));
//        dataForCalc.put("coefficientOfFrictionThread", "0.14");
//        dataForCalc.put("coefficientOfFrictionBoltHead", "0.14");
//        dataForCalc.put("diametrHead_mm", String.valueOf(threadCurrent.getDHead_mm()));
//        dataForCalc.put("diametrHole_mm", String.valueOf(threadCurrent.getDhole_mm()));
//        dataForCalc.put("momentKellerman_NM", "0");
//
//
//            /**
//             * установка значений  из формы
//             */
//
//            if (request.getParameter("limateStrengthBolt_Mpa") != null) {
//
//                dataForCalc.put("limateStrengthBolt_Mpa", request.getParameter("limateStrengthBolt_Mpa"));
//            }
//
//        if (Objects.nonNull(request.getParameter("limateStrengthScrew_Mpa"))) {
//
//            dataForCalc.put("limateStrengthScrew_Mpa", request.getParameter("limateStrengthScrew_Mpa"));
//        }
//
//            if (Objects.nonNull(request.getParameter("diametrThread_mm"))) {
//
//                dataForCalc.put("diametrThread_mm", request.getParameter("diametrThread_mm"));
//            }
//
//            if (Objects.nonNull(request.getParameter("middleDiamThread_mm"))) {
//
//                dataForCalc.put("middleDiamThread_mm", request.getParameter("middleDiamThread_mm"));
//            }
//
//            if (Objects.nonNull(request.getParameter("k_threadDepth"))) {
//
//                dataForCalc.put("k_threadDepth", request.getParameter("k_threadDepth"));
//            }
//
//            if (Objects.nonNull(request.getParameter("safetyFactor"))) {
//                dataForCalc.put("safetyFactor", request.getParameter("safetyFactor"));
//            }
//
//        if (Objects.nonNull(request.getParameter("stepThread_mm"))) {
//            dataForCalc.put("stepThread_mm", request.getParameter("stepThread_mm"));
//        }
//
//        if (Objects.nonNull(request.getParameter("coefficientOfFrictionThread"))) {
//
//            dataForCalc.put("coefficientOfFrictionThread", request.getParameter("coefficientOfFrictionThread"));
//        }
//
//        if (Objects.nonNull(request.getParameter("coefficientOfFrictionBoltHead"))) {
//
//            dataForCalc.put("coefficientOfFrictionBoltHead", request.getParameter("coefficientOfFrictionBoltHead"));
//        }
//
//        if (Objects.nonNull(request.getParameter("diametrHead_mm"))) {
//
//            dataForCalc.put("diametrHead_mm", request.getParameter("diametrHead_mm"));
//        }
//
//        if (Objects.nonNull(request.getParameter("diametrHole_mm"))) {
//            dataForCalc.put("diametrHole_mm", request.getParameter("diametrHole_mm"));
//        }
///**
// * Вычисление сил моментов и напряжения
// */
//            String noBolt = request.getParameter("no_bolt"); // учитывать ли болт при расчетах, получение параметра
//
//        /**
//         * передача в модель вспомогательного параметра no_bolt для отображения чекбокс
//         */
//        if(Objects.nonNull(noBolt)){
//                model.addAttribute("no_bolt", 1);
//            }
//            else {
//                model.addAttribute("no_bolt", 0);
//            }
//            /**
//             * Допустимые силы в резьбах и болте
//             */
//                   int powerMaxByThreadBolt_N = momentService.powerMaxByLimitInThread_N(
//                    Integer.parseInt(dataForCalc.get("limateStrengthBolt_Mpa"))
//                    , Double.parseDouble(dataForCalc.get("diametrThread_mm"))
//                    , Double.parseDouble(dataForCalc.get("middleDiamThread_mm"))
//                    , Double.parseDouble(dataForCalc.get("k_threadDepth"))
//                    , Double.parseDouble(dataForCalc.get("safetyFactor")));
//
//        int powerMaxByThreadScrew_N = momentService.powerMaxByLimitInThread_N(
//                Integer.parseInt(dataForCalc.get("limateStrengthScrew_Mpa"))
//                , Double.parseDouble(dataForCalc.get("diametrThread_mm"))
//                , Double.parseDouble(dataForCalc.get("middleDiamThread_mm"))
//                , Double.parseDouble(dataForCalc.get("k_threadDepth"))
//                , Double.parseDouble(dataForCalc.get("safetyFactor")));
//
//            int powerMaxByBolt_N = momentService.powerMaxByLimitInBoltRot_N (
//                    Integer.parseInt(dataForCalc.get("limateStrengthBolt_Mpa")),
//                    Double.parseDouble(dataForCalc.get("diametrThread_mm"))
//                    , Double.parseDouble(dataForCalc.get("safetyFactor")));
//
//        /**
//         * максимально допустимая сила
//         */
//        int powerMaxLimitForMaterial_N = momentService.powerMax_N(powerMaxByBolt_N
//                        ,powerMaxByThreadBolt_N
//                        , powerMaxByThreadScrew_N
//                        ,noBolt);
//
//        dataForCalc.put("powerMaxForMaterial_kgs", String.valueOf(powerMaxLimitForMaterial_N * 0.1));
//
//        /**
//         * напрежение в стержне болта
//         */
//        int stregthBoltRot_Mpa = momentService.strengthInBoltRot_Mpa(powerMaxLimitForMaterial_N, Double.parseDouble(dataForCalc.get("diametrThread_mm")));
//
//        dataForCalc.put("stregthBoltRot_Mpa", String.valueOf(stregthBoltRot_Mpa));
//
//        int strengthThread_Mpa = momentService.strengthInThread_Mpa(
//                powerMaxLimitForMaterial_N,
//                Double.parseDouble(dataForCalc.get("diametrThread_mm")),
//                Double.parseDouble(dataForCalc.get("middleDiamThread_mm")),
//                Double.parseDouble(dataForCalc.get("k_threadDepth")));
//
//        dataForCalc.put("stregthTread_Mpa", String.valueOf(strengthThread_Mpa));
//
//        double momentKellerman_NM = momentService.momentKellerman_NM (powerMaxLimitForMaterial_N
//                ,Double.parseDouble(dataForCalc.get("diametrThread_mm"))
//                ,Double.parseDouble(dataForCalc.get("stepThread_mm"))
//                ,Double.parseDouble(dataForCalc.get("coefficientOfFrictionThread"))
//                ,Double.parseDouble(dataForCalc.get("coefficientOfFrictionBoltHead"))
//                ,Double.parseDouble(dataForCalc.get("diametrHead_mm"))
//                ,Double.parseDouble(dataForCalc.get("diametrHole_mm")));
//
//        dataForCalc.put("momentKellerman_NM", String.valueOf(momentKellerman_NM));
//
///**
// * обнуление всех кукиес cookies для материала
// */
//
//        Map<String, Cookie> newCookies = new HashMap<>();
//
//        if(Objects.nonNull(cookies)) {
//            for (Cookie cookie : cookies) {
//
//                if (cookie.getName().equals(CookiesParametr.USERID.getParam()) ||
//                        cookie.getName().equals(CookiesParametr.USERNAME.getParam()) ||
//                        cookie.getName().equals(CookiesParametr.USERROLE.getParam())) {
//                    newCookies.put(cookie.getName(), cookie);
//                    continue;
//                }
//                cookie.setValue("0");
//                cookie.setMaxAge(0);
//                newCookies.put(cookie.getName(), cookie);
////
//            }
//        }
///**
// * Установка новых cookies для отображения материала на странице
// */
//            for (MaterialsEntity mat : materialsEntities) {
//                Cookie cookie = new Cookie(CookiesParametr.ID.getParam() + String.valueOf(mat.getId()), "1");
//                newCookies.put(cookie.getName(), cookie);
////
//            }
//
//        /**
//         * Установка куков для резьбы
//         */
//        newCookies.put(CookiesParametr.FREADID.getParam(), new Cookie(CookiesParametr.FREADID.getParam(), String.valueOf(threadCurrent.getId())));
//
//        /**
//         * добавление куков responce
//             */
//
//        response.addCookie(new Cookie("her2", "her2"));
//
//
//        Collection<Cookie> collectionCookies = newCookies.values();     // коллекция куков из map куков
//        ArrayList<Cookie> arrayListCookies = new ArrayList<>(collectionCookies); // лист куков из коллекции
//
//
//        Cookie[] newCookiesResponce = arrayListCookies.toArray(new Cookie[0]); // преобраз. листа куков в массив
//
//        for (Cookie cookie: newCookiesResponce){ // добавление куков responce
//            response.addCookie(cookie);
//        }
//
//
//        model.addAttribute("dataForCalc", dataForCalc);
//
//
//            return "moment_page_1.html";
//        }

    /**
     * Загрузка главной страницы (new)
     */
    @SoutAOP
    @GetMapping("/") //moment_page_1
    public String moment_page_1(@RequestParam(value = "threadId", required = false) Integer threadId
            , Model model, HttpServletRequest request, HttpServletResponse response) throws InterruptedException {  // модель для отображения во view
        System.out.println("@GetMapping(/moment_page_1");

        int userId = 0;

        Cookie[] cookies = request.getCookies();

        /**
         * Получение пользователя из куков для модели
         */

        model.addAttribute(AttributeName.USERNAME.getParam(),"Enter");
        if(nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CookiesParametr.USERNAME.getParam())) {

                    model.addAttribute("userName", cookie.getValue());
                }
                if (cookie.getName().equals(CookiesParametr.USERID.getParam())) {

                    userId = Integer.parseInt(cookie.getValue());
                }

            }
        }

        /**
         * Получение списка болтов юзера и админа из БД*/

        List<BoltEntity> usersBolt = boltService.boltsByUserId(userId);

        List<BoltEntity> adminBolt = boltService.boltsAdmin();

        List<BoltEntity> allBolt = boltService.allBoltAdminUser(userId, usersBolt,adminBolt);

        model.addAttribute("bolts", allBolt);


        /**
         * Получение списка гаек от юзера и админа из БД
         */
        List<SqrewEntity> userSqrews = sqrewService.getSqrewsByUserId(userId);

        List<SqrewEntity> adminSqrews = sqrewService.getSqrewsAdmin();

        List<SqrewEntity> allSqrews = sqrewService.getAllSqrewAdminUser(userId, userSqrews, adminSqrews);

        model.addAttribute("sqrews", allSqrews);


        /**
         *получение резьб из БД - переделать сортировку
         */
//        List<Moments_db> moments_db = moments_db_dao.getMoments_db();

        List<ThreadEntity> threadEntityNoSorted = threadDao.getThread();

        /**
         * сортировка листа резьб по возрастанию
         */
        List<ThreadEntity> threadEntitySorted = threadEntityNoSorted.stream()
                .sorted((thr1, thr2) -> thr1.compareTo(thr2))
                .collect(Collectors.toList());

        model.addAttribute("threads", threadEntitySorted);



        /**
         * Установка текущего значения резьбы threadCurrent:
         * или  = threadEntitySorted.get(0)
         * или по threadId
         * или по cookies
         */
        ThreadEntity threadCurrent = threadEntitySorted.get(0);


        if (threadId != null) {

            threadCurrent = threadEntitySorted.stream()
                    .filter(thread -> thread.getId() == threadId).findAny().orElse(threadEntitySorted.get(0));

            model.addAttribute("thread", threadCurrent.getThread());//для вспомогательной переменной отображения выбора резьбы


        }
        if(nonNull(cookies) && threadId == null) {

            Cookie cookieThread = Arrays.stream(cookies)
                    .filter(f -> f.getName().equals(CookiesParametr.FREADID.getParam())).findAny().orElse(null);

            if (nonNull(cookieThread)) {

                threadCurrent = threadEntitySorted.stream().filter(thread_db -> String.valueOf(thread_db.getId()).equals(cookieThread.getValue()))
                        .findAny().orElse(threadEntitySorted.get(0));

                model.addAttribute("thread", threadCurrent.getThread());

            }
        }
        model.addAttribute("thread", threadCurrent.getThread());

        /**
         * Получение парметров ввода из формы
         */
        Map<String, String> dataForForm = null;

        try {
            dataForForm = controllerService.getParametrFromForm(
                    request,
                    materialService.getMaterialParamDefoult(),
                    threadCurrent,
                    boltService.getBoltByRequestBoltIdOrParam(allBolt, request),
                    sqrewService.getSqrewByRequestSqrewIdOrParam(allSqrews, request));
        }
        catch (FormatException f){
            f.printStackTrace();
            model.addAttribute("error", f.getMessage());

//            return "return moment_page_1.html";
            dataForForm = controllerService.getParametrFormDefoult(materialService.getMaterialParamDefoult(),
                    threadCurrent,
                    boltService.getBoltByRequestBoltIdOrParam(allBolt, request),
                    sqrewService.getSqrewByRequestSqrewIdOrParam(allSqrews, request));
        }


        Map<String, String> dataFormDefoult = controllerService.getParametrFormDefoult(
                materialService.getMaterialParamDefoult(),
                threadCurrent,
                boltService.getBoltByRequestBoltIdOrParam(allBolt, request),
                sqrewService.getSqrewByRequestSqrewIdOrParam(allSqrews, request)
        );

        Map<String, String> atributeClassForForm = controllerService.setClassAtributeForForm(
                dataFormDefoult, dataForForm);

        model.addAttribute("atributeClass", atributeClassForForm);



        /**
         * Резьба по парметрам из формы пользователя - ОСНОВНОЙ для вычислений от threadCurrent
         */
        ThreadEntity threadByParametr = threadService.getThreadByRequestParam(dataForForm, threadCurrent);

        /**
         * Установка текущих значений болта и гайки в форму по данныйм из предыдущей формы
         * (для формы селект)
         */
        model.addAttribute("bolt", boltService.getBoltByRequestBoltIdOrParam(allBolt, request));
        model.addAttribute("sqrew", sqrewService.getSqrewByRequestSqrewIdOrParam(allSqrews, request));


        /**
         * вычисление момента, силы, напряжений по соответствию с id гайки, материала, текущей резьбой, параметрами
         * материала из формы
         * Для главной страницы
         */
        List<Integer> powerList = momentService.powerMaxForBoltSqrewList( // убрать из метода materialService.getMaterialByRequestParam
                boltService.getBoltByRequestBoltIdOrParam(allBolt, request),
                sqrewService.getSqrewByRequestSqrewIdOrParam(allSqrews, request),
                threadByParametr,
                dataForForm);

        int powerMaxBoltSqrew_N =  powerList.get(0);

        int powerMaxBoltSqrew_kgs = momentService.powerMaxForMaterial_kgs(powerMaxBoltSqrew_N);

        double momentForBoltSqrew_Nm = momentService.momentsKallermanForBoltSqrew_N(boltService.getBoltByRequestBoltIdOrParam(allBolt, request),
                    sqrewService.getSqrewByRequestSqrewIdOrParam(allSqrews, request),
                    threadByParametr,
                    materialService.getMaterialByRequestParam(dataForForm),
                    dataForForm);

        int stregthInBoltRot_MPa = momentService.strengthInBoltRot_Mpa(
                powerMaxBoltSqrew_N,
                threadByParametr.getDBolt_mm());


//        int stregthInThread_MPa = momentService.strengthInThread_Mpa(
//                powerMaxBoltSqrew_N,
//                threadByParametr.getDBolt_mm(),
//                Double.parseDouble(dataForForm.get("middleDiamThread_mm")),
//                Double.parseDouble(dataForForm.get("k_threadDepth")));

        model.addAttribute("momentForBoltSqrew_Nm", new DecimalFormat("#.#").format(momentForBoltSqrew_Nm));
        model.addAttribute("powerMaxBoltSqrew_kgs", powerMaxBoltSqrew_kgs);
        model.addAttribute("stregthInBoltRot_MPa", stregthInBoltRot_MPa);
        model.addAttribute("listMaxPower", powerList);
//        model.addAttribute("stregthInThread_MPa", stregthInThread_MPa);


/**
 * Распределение напряжени по виткам резбы
 */
        List<String>strengthInTurnList = momentService.strengthInTurnList_Mpa(
                powerMaxBoltSqrew_N,
                threadByParametr,
                toDouble(dataForForm.get("stepThread_mm"))
                , toDouble(dataForForm.get("middleDiamThread_mm"))
                , toDouble(dataForForm.get("k_threadDepth")));


        model.addAttribute("strengthInTurn", strengthInTurnList);

        /**
         * Напряжение на первом витке
         */
        model.addAttribute("strengthInOneTurn", strengthInTurnList.get(0).substring(3));

        //Количество рабочих витков

        model.addAttribute("countWorkTurn", momentService.countWorkTurn(
                threadByParametr.getDBolt_mm(),
                toDouble(dataForForm.get("k_threadDepth")),
                toDouble(dataForForm.get("stepThread_mm"))));

        //Высота гайки
        model.addAttribute("hSqrew",
                new DecimalFormat("#,#")
                        .format(momentService.hSqrew_mm(threadCurrent
                        ,toDouble(dataForForm.get("k_threadDepth")))));


/**
 * обнуление всех кукиес cookies для материала
 */

        Map<String, Cookie> newCookies = new HashMap<>();

        if(nonNull(cookies)) {
            for (Cookie cookie : cookies) {

                if (cookie.getName().equals(CookiesParametr.USERID.getParam()) ||
                        cookie.getName().equals(CookiesParametr.USERNAME.getParam()) ||
                        cookie.getName().equals(CookiesParametr.USERROLE.getParam())) {
                    newCookies.put(cookie.getName(), cookie);
                    continue;
                }
                cookie.setValue("0");
                cookie.setMaxAge(0);
                newCookies.put(cookie.getName(), cookie);
//
            }
        }
/**
 * Установка новых cookies для отображения материала на странице
 */
//        for (MaterialsEntity mat : materialsEntities) {
//            Cookie cookie = new Cookie(CookiesParametr.ID.getParam() + String.valueOf(mat.getId()), "1");
//            newCookies.put(cookie.getName(), cookie);
//
//        }

        /**
         * Установка куков для резьбы
         */
        newCookies.put(CookiesParametr.FREADID.getParam(), new Cookie(CookiesParametr.FREADID.getParam(), String.valueOf(threadCurrent.getId())));

        /**
         * добавление куков responce
         */

        response.addCookie(new Cookie("her2", "her2"));


        Collection<Cookie> collectionCookies = newCookies.values();     // коллекция куков из map куков
        ArrayList<Cookie> arrayListCookies = new ArrayList<>(collectionCookies); // лист куков из коллекции


        Cookie[] newCookiesResponce = arrayListCookies.toArray(new Cookie[0]); // преобраз. листа куков в массив

        for (Cookie cookie: newCookiesResponce){ // добавление куков responce
            response.addCookie(cookie);
        }


        model.addAttribute("dataForForm", dataForForm);

        System.out.println("stepThread_mm_hidden" + request.getParameter("stepThread_mm_hidden"));


        return "moment_page_1.html";
    }



    @GetMapping("/moment_page_1/{id}")
        public String viewOneMaterial ( @PathVariable("id") int id, HttpServletRequest request, Model model){

            MaterialsEntity materals_entity = materialsDao.getMaterialsById(id);
            List<MomentsEntity> moments_entities = materals_entity.getMomentsEntity();

            if (nonNull(materals_entity.getDataCreate())) {

                DateTimeFormatter dataFormater = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
                String data = dataFormater.format(materals_entity.getDataCreate());

                model.addAttribute("data", data);
            }


            model.addAttribute("material", materals_entity);
            model.addAttribute("moments", moments_entities);

            return "OneMaterial.html";
        }

        /**
         на вычисление момментов и силы по параметрам
         * @return загружает начальную страницу
         */
        @GetMapping("/moment_calc")
        public String momentCalc (Model model, HttpServletRequest request, HttpServletResponse response){

            response.addCookie(new Cookie("her", "her"));

            return "include:/moment_page_1";
        }

    /**
     * Загрузка страницы расчетов по параметрам
     */
    @GetMapping("/moment_parametr") //moment_parametr.html
    public String momentByParametr(@RequestParam(value = "threadId", required = false) Integer threadId
            , Model model, HttpServletRequest request, HttpServletResponse response) {  // модель для отображения во view

        int userId = 0;

        Cookie[] cookies = request.getCookies();

        /**
         * Получение пользователя из куков для модели
         */

        model.addAttribute(AttributeName.USERNAME.getParam(),"Enter");
                if(nonNull(cookies)) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals(CookiesParametr.USERNAME.getParam())) {

                            model.addAttribute("userName", cookie.getValue());
                        }
                        if (cookie.getName().equals(CookiesParametr.USERID.getParam())) {

                            userId = Integer.parseInt(cookie.getValue());
                        }

                    }
                }


        /**
         *получение и резьб из БД - переделать моменты
         */


        List<ThreadEntity> threadEntityNoSorted = threadDao.getThread();

        /**
         * to do
         * сортировка листа резьб по возрастанию
         */
        List<ThreadEntity> threadEntitySorted = threadEntityNoSorted.stream()
                .sorted(ThreadEntity::compareTo)
                .collect(Collectors.toList());

        model.addAttribute("threads", threadEntitySorted);


        /**
         * Установка текущего значения резьбы threadCurrent:
         * или  = threadEntitySorted.get(0)
         * или по threadId
         * или по cookies
         */
        ThreadEntity threadCurrent = threadEntitySorted.get(0);


        if (threadId != null) {

            threadCurrent = threadEntitySorted.stream()
                    .filter(thread -> thread.getId() == threadId).findAny().orElse(threadEntitySorted.get(0));

            model.addAttribute("thread", threadCurrent.getThread());//для вспомогательной переменной отображения выбора резьбы


        }
        if(nonNull(cookies) && threadId == null) {

            Cookie cookieThread = Arrays.stream(cookies)
                    .filter(f -> f.getName().equals(CookiesParametr.FREADID.getParam())).findAny().orElse(null);

            if (nonNull(cookieThread)) {
//                moments_dbsFilter = moments_db.stream()
//                        .filter(f -> String.valueOf(f.getThread().getId()).equals(cookieThread.getValue())).toList();

                threadCurrent = threadEntitySorted.stream().filter(thread_db -> String.valueOf(thread_db.getId()).equals(cookieThread.getValue()))
                        .findAny().orElse(threadEntitySorted.get(0));

                model.addAttribute("thread", threadCurrent.getThread());

            }
        }

        model.addAttribute("thread", threadCurrent.getThread());


            /**
             * форма вычисления момента и силы
             */
            Map<String, String> dataForCalc = new HashMap<>();

        dataForCalc.put("limateStrengthBolt_Mpa", "800");
        dataForCalc.put("limateStrengthScrew_Mpa", "700");
        dataForCalc.put("diametrThread_mm", String.valueOf(threadCurrent.getDBolt_mm()));
        dataForCalc.put("middleDiamThread_mm", String.valueOf(threadCurrent.getDMidlethread_mm()));
        dataForCalc.put("k_threadDepth", "0.8");
        dataForCalc.put("safetyFactor", "0.75");
        dataForCalc.put("powerMaxForMaterial_kgs", "0");

        dataForCalc.put("stepThread_mm", String.valueOf(threadCurrent.getStepThread_mm()));
        dataForCalc.put("coefficientOfFrictionThread", "0.14");
        dataForCalc.put("coefficientOfFrictionBoltHead", "0.14");
        dataForCalc.put("diametrHead_mm", String.valueOf(threadCurrent.getDHead_mm()));
        dataForCalc.put("diametrHole_mm", String.valueOf(threadCurrent.getDhole_mm()));
        dataForCalc.put("momentKellerman_NM", "0");


            /**
             * установка значений из формы в dataForCalc для текущих расчетов
             */

            if (Strings.isNotBlank(request.getParameter("limateStrengthBolt_Mpa"))) {

                dataForCalc.put("limateStrengthBolt_Mpa", request.getParameter("limateStrengthBolt_Mpa"));
            }

        if (Strings.isNotBlank(request.getParameter("limateStrengthScrew_Mpa"))) {

            dataForCalc.put("limateStrengthScrew_Mpa", request.getParameter("limateStrengthScrew_Mpa"));
        }

            if (Strings.isNotBlank(request.getParameter("diametrThread_mm"))) {

                dataForCalc.put("diametrThread_mm", request.getParameter("diametrThread_mm"));
            }

            if (Strings.isNotBlank(request.getParameter("middleDiamThread_mm"))) {

                dataForCalc.put("middleDiamThread_mm", request.getParameter("middleDiamThread_mm"));
            }

            if (Strings.isNotBlank(request.getParameter("k_threadDepth"))) {

                dataForCalc.put("k_threadDepth", request.getParameter("k_threadDepth"));
            }

            if (Strings.isNotBlank(request.getParameter("safetyFactor"))) {
                dataForCalc.put("safetyFactor", request.getParameter("safetyFactor"));
            }

        if (Strings.isNotBlank(request.getParameter("stepThread_mm"))) {
            dataForCalc.put("stepThread_mm", request.getParameter("stepThread_mm"));
        }

        if (Strings.isNotBlank(request.getParameter("coefficientOfFrictionThread"))) {

            dataForCalc.put("coefficientOfFrictionThread", request.getParameter("coefficientOfFrictionThread"));
        }

        if (Strings.isNotBlank(request.getParameter("coefficientOfFrictionBoltHead"))) {

            dataForCalc.put("coefficientOfFrictionBoltHead", request.getParameter("coefficientOfFrictionBoltHead"));
        }

        if (Strings.isNotBlank(request.getParameter("diametrHead_mm"))) {

            dataForCalc.put("diametrHead_mm", request.getParameter("diametrHead_mm"));
        }

        if (Strings.isNotBlank(request.getParameter("diametrHole_mm"))) {
            dataForCalc.put("diametrHole_mm", request.getParameter("diametrHole_mm"));
        }
/**
 * Вычисление сил моментов и напряжения
 */
            String noBolt = request.getParameter("no_bolt"); // учитывать ли болт при расчетах, получение параметра

        /**
         * передача в модель вспомогательного параметра no_bolt для отображения чекбокс
         */
        if(nonNull(noBolt)){
                model.addAttribute("no_bolt", 1);
            }
            else {
                model.addAttribute("no_bolt", 0);
            }
            /**
             * Допустимые силы в резьбах(среднее) и болте
             */
                   int powerMaxByThreadBolt_N = momentService.powerMaxByLimitInThread_N(
                    Integer.parseInt(dataForCalc.get("limateStrengthBolt_Mpa"))
                    , Double.parseDouble(dataForCalc.get("diametrThread_mm"))
                    , Double.parseDouble(dataForCalc.get("middleDiamThread_mm"))
                    , Double.parseDouble(dataForCalc.get("k_threadDepth"))
                    , Double.parseDouble(dataForCalc.get("safetyFactor")));

//        int powerMaxByThreadBolt_N = momentService.powerMaxByLimitOnOneTrust_N(
//                toDouble(dataForCalc.get("diametrThread_mm")),
//                toDouble(dataForCalc.get("middleDiamThread_mm")),
//                toDouble(dataForCalc.get("stepThread_mm")),
//                toInt(dataForCalc.get("limateStrengthBolt_Mpa")),
//                toDouble(dataForCalc.get("safetyFactor")),
//                toDouble(dataForCalc.get("k_threadDepth")));


        int powerMaxByThreadScrew_N = momentService.powerMaxByLimitInThread_N(
                Integer.parseInt(dataForCalc.get("limateStrengthScrew_Mpa"))
                , Double.parseDouble(dataForCalc.get("diametrThread_mm"))
                , Double.parseDouble(dataForCalc.get("middleDiamThread_mm"))
                , Double.parseDouble(dataForCalc.get("k_threadDepth"))
                , Double.parseDouble(dataForCalc.get("safetyFactor")));

//        int powerMaxByThreadScrew_N = momentService.powerMaxByLimitOnOneTrust_N(
//                toDouble(dataForCalc.get("diametrThread_mm")),
//                toDouble(dataForCalc.get("middleDiamThread_mm")),
//                toDouble(dataForCalc.get("stepThread_mm")),
//                toInt(dataForCalc.get("limateStrengthScrew_Mpa")),
//                toDouble(dataForCalc.get("safetyFactor")),
//                toDouble(dataForCalc.get("k_threadDepth")));


            int powerMaxByBolt_N = momentService.powerMaxByLimitInBoltRot_N (
                    toInt(dataForCalc.get("limateStrengthBolt_Mpa")),
                    toDouble(dataForCalc.get("diametrThread_mm"))
                    , toDouble(dataForCalc.get("safetyFactor")));

        /**
         * максимально допустимая сила из трех ранее вычисленных
         */
        int powerMaxLimitForMaterial_N = momentService.powerMax_N(powerMaxByBolt_N
                        ,powerMaxByThreadBolt_N
                        , powerMaxByThreadScrew_N
                        ,noBolt);

        dataForCalc.put("powerMaxForMaterial_kgs", String.valueOf((int) (powerMaxLimitForMaterial_N * 0.1)));

        /**
         * напряжение в стержне болта
         */
        int stregthBoltRot_Mpa = momentService.strengthInBoltRot_Mpa(powerMaxLimitForMaterial_N, Double.parseDouble(dataForCalc.get("diametrThread_mm")));

        dataForCalc.put("stregthBoltRot_Mpa", String.valueOf(stregthBoltRot_Mpa));

        int strengthThread_Mpa = momentService.strengthInThread_Mpa(
                powerMaxLimitForMaterial_N,
                Double.parseDouble(dataForCalc.get("diametrThread_mm")),
                Double.parseDouble(dataForCalc.get("middleDiamThread_mm")),
                Double.parseDouble(dataForCalc.get("k_threadDepth")));

        dataForCalc.put("stregthTread_Mpa", String.valueOf(strengthThread_Mpa));

        double momentKellerman_NM = momentService.momentKellerman_NM (powerMaxLimitForMaterial_N
                ,Double.parseDouble(dataForCalc.get("diametrThread_mm"))
                ,Double.parseDouble(dataForCalc.get("stepThread_mm"))
                ,Double.parseDouble(dataForCalc.get("coefficientOfFrictionThread"))
                ,Double.parseDouble(dataForCalc.get("coefficientOfFrictionBoltHead"))
                ,Double.parseDouble(dataForCalc.get("diametrHead_mm"))
                ,Double.parseDouble(dataForCalc.get("diametrHole_mm")));

        dataForCalc.put("momentKellerman_NM",new DecimalFormat("#.#").format(momentKellerman_NM));

/**
 * обнуление всех кукиес cookies для материала
 */

        Map<String, Cookie> newCookies = new HashMap<>();

        if(nonNull(cookies)) {
            for (Cookie cookie : cookies) {

                if (cookie.getName().equals(CookiesParametr.USERID.getParam()) ||
                        cookie.getName().equals(CookiesParametr.USERNAME.getParam()) ||
                        cookie.getName().equals(CookiesParametr.USERROLE.getParam())) {
                    newCookies.put(cookie.getName(), cookie);
                    continue;
                }
                cookie.setValue("0");
                cookie.setMaxAge(0);
                newCookies.put(cookie.getName(), cookie);
//
            }
        }


        /**
         * Установка куков для резьбы
         */
        newCookies.put(CookiesParametr.FREADID.getParam(), new Cookie(CookiesParametr.FREADID.getParam(), String.valueOf(threadCurrent.getId())));


        Collection<Cookie> collectionCookies = newCookies.values();     // коллекция куков из map куков
        ArrayList<Cookie> arrayListCookies = new ArrayList<>(collectionCookies); // лист куков из коллекции


        Cookie[] newCookiesResponce = arrayListCookies.toArray(new Cookie[0]); // преобраз. листа куков в массив

        for (Cookie cookie: newCookiesResponce){ // добавление куков responce
            response.addCookie(cookie);
        }


        model.addAttribute("dataForCalc", dataForCalc);


            return "moment_parametr.html";
        }

    }
