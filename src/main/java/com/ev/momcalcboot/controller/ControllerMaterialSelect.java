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

//  Значение по умолчанию
    int userId = 0;
    String userName = "No User";

    @GetMapping("/material_select")
    private String materialSelect(Model model, HttpServletRequest request) {

        List<MaterialsEntity> adminMaterials = new ArrayList<>();
        List<MaterialsEntity> usersMaterials = new ArrayList<>();

        /**
         * ????????? ?????????? ? id user = 1 (admin)
         */
        if (!materialsDao.getMaterialsByUserId(1).isEmpty()) {
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
            if (cookie.getName().equals(CookiesParametr.USERNAME.getParam())) {
                userName = cookie.getValue();
            }
        }

        /**
         * ????????? ?????????? ?????
         */
        if (userId > 1) {
            usersMaterials.addAll(materialsDao.getMaterialsByUserId(userId));

        }

        model.addAttribute("materialsAdmin", adminMaterials);
        model.addAttribute("materialsUser", usersMaterials);

        model.addAttribute("userName", userName);

        return "material_select.html";
    }

}
