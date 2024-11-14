package com.ev.momcalcboot.controller;

import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.dao.MaterialsDao;
import com.ev.momcalcboot.enums.CookiesParametr;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ControllerMaterialSelect {

    @Autowired
    private MaterialsDao materialsDao;

    int userId = 0;

    String userName = "No User";


    @GetMapping("/material_select")
    private String materialSelect(Model model, HttpServletRequest request){

        List<MaterialsEntity> adminMaterials = new ArrayList<>();
        List<MaterialsEntity> usersMaterials = new ArrayList<>();

        /**
         * ????????? ?????????? ? id user = 1 (admin)
         */
        if ( ! materialsDao.getMaterialsByUserId(1).isEmpty()){
            adminMaterials.addAll(materialsDao.getMaterialsByUserId(1));
        }

        /**
         * ????????? ????? ?? ?????
         */
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(CookiesParametr.USERID.getParam())) {
                userId = Integer.parseInt(cookie.getValue());
            }
            if (cookie.getName().equals(CookiesParametr.USERNAME.getParam())){
                userName = cookie.getValue();
            }
        }

        /**
         * ????????? ?????????? ?????
         */
        if (userId >1){
            usersMaterials.addAll(materialsDao.getMaterialsByUserId(userId));

        }

        model.addAttribute("materialsAdmin", adminMaterials);
        model.addAttribute("materialsUser", usersMaterials);

        model.addAttribute("userName", userName);

        return "material_select.html";
    }


//    @GetMapping("/material_select_app")
//    private String materialSelectapply(@RequestParam(value = "threadId", required = false)Integer threadId, Model model
//            , HttpServletRequest request){
//
//
//            List<Materals_db> Allmaterals_dbs = materals_db_dao.getMaterals_db();
//
//            List<Materals_db> materals_dbs = Allmaterals_dbs.stream()
//                    .filter(m -> String.valueOf(m.getId()).equals(request.getParameter((String.valueOf(m.getId())))))
//                    .toList();
//
//
//            model.addAttribute("materials", materals_dbs);
//
//            List<Moments_db> moments_db = moments_db_dao.getMoments_db();
//
//            List<Thread_db> thread_dbs = thread_db_dao.getThread_db();
//            model.addAttribute("threads", thread_dbs);
//
//            List<Moments_db> moments_dbsFilter;
//
//            if (threadId != null) {
//                moments_dbsFilter = moments_db.stream()
//                        .filter(f -> f.getThread().getId() == threadId)
//                        .toList();
//            }
//            else {
//                moments_dbsFilter = moments_db.stream().filter(f -> f.getThread().getId() == thread_dbs.get(0).getId()).toList();
//            }
//
//            List<Moments_db> moments_dbSorted = new ArrayList<>();
//
//            for (Materals_db mat: materals_dbs){
//
//                moments_dbSorted.add(moments_dbsFilter.stream().filter(f -> f.getMaterals_db().getId() == mat.getId()).findAny().orElse(null));
//            }
//            model.addAttribute("moments", moments_dbSorted);
//
////        System.out.println(request.getParameter("47"));
//
//        return "redirect:/moment_page_1";
//    }

}
