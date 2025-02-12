package com.ev.momcalcboot.controller;

import com.ev.momcalcboot.Entity.SqrewEntity;
import com.ev.momcalcboot.enums.CookiesParametr;
import com.ev.momcalcboot.repositoriy.UserDaoRepository;
import com.ev.momcalcboot.service.internal.CookService;
import com.ev.momcalcboot.service.internal.SqrewService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Controller()
public class ControllerSqrew {

    @Autowired
    private final SqrewService sqrewService;

    @Autowired
    private final UserDaoRepository userDaoRepository;

    @Autowired
    private final CookService cookService;


    private  List<SqrewEntity> sqrewListTemp;

    @GetMapping("/all_sqrew")
    public String viewAllSqrew(Model model, HttpServletRequest request){

        /**
         * получение id польз. из куков
         */
        Cookie cookieUserId = cookService.findCookByName(request, CookiesParametr.USERID.getParam());
        // если пользователь не получен - вернуть на регистрацию
        if (Objects.isNull(cookieUserId))
        {
            return "redirect:/user_registration";
        }

         Integer userId = Integer.parseInt(cookService.findCookByName(request, CookiesParametr.USERID.getParam()).getValue());
        model.addAttribute("user", userDaoRepository.getUserById(userId));

        /**
         * Список болтов админа (id =1)
         */
        model.addAttribute("sqrewsAdmin", sqrewService.getSqrewsByUserId(1));

        /**
         * Список болтов польз по его id
         */
        sqrewListTemp.clear();
        sqrewListTemp.addAll(sqrewService.getSqrewsByUserId(userId));
        model.addAttribute("sqrewsUser", sqrewListTemp);

     return "all_sqrew.html";
 }

        @PostMapping("/all_sqrew")
        public String saveChangeSqrew(HttpServletRequest request){

            Integer userId = Integer.parseInt(cookService.findCookByName(request, CookiesParametr.USERID.getParam()).getValue());

            sqrewService.saveSqrewsByParametrForm(request, sqrewListTemp, userId);

            sqrewService.saveNewSqrew(userId, request);

            return "redirect:/all_sqrew";
        }

        @GetMapping("/all_sqrew/{id}")
        public String deleteSqrew(@PathVariable("id") int sqrewId){

        sqrewService.deleteSqrew(sqrewId);

        return "redirect:/all_sqrew";

        }
}
