package com.ev.momcalcboot.controller;

import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.enums.CookiesParametr;
import com.ev.momcalcboot.exceptions.FormatException;
import com.ev.momcalcboot.repositoriy.UserDaoRepository;
import com.ev.momcalcboot.service.internal.BoltService;
import com.ev.momcalcboot.service.internal.CookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@Controller()
@Slf4j
public class ControllerBolt {

    @Autowired
    private final BoltService boltService;

    @Autowired
    private final UserDaoRepository userDaoRepository;

    @Autowired
    private final CookService cookService;


    private  List<BoltEntity> boltListTemp;

    @GetMapping("/all_bolt")
    public String viewAllBolt(Model model, HttpServletRequest request){
        log.info("Открытие страницы с болтами");
        /**
         * получение польз. из куков
         */


         Integer userId = Integer.parseInt(cookService.findCookByName(request, CookiesParametr.USERID.getParam()).getValue());
        model.addAttribute("user", userDaoRepository.getUserById(userId));

        String userName = cookService.findCookByName(request, CookiesParametr.USERNAME.getParam()).getValue();

        log.info("Получен пользователь из куков id = {}, имя: {}", userId, userName);

        /**
         * Список болтов админа (id =1)
         */
        model.addAttribute("boltsAdmin", boltService.getBoltByUserId(1));

        /**
         * Список болтов польз по его id
         */
        boltListTemp.clear();
        boltListTemp.addAll(boltService.getBoltByUserId(userId));
        model.addAttribute("boltsUser", boltListTemp);

     return "all_bolt.html";
 }

        @PostMapping("/all_bolt")
        public String saveChangeBolt(HttpServletRequest request){

            Integer userId = Integer.parseInt(cookService.findCookByName(request, CookiesParametr.USERID.getParam()).getValue());

            boltService.saveBoltsByParametrForm(request, boltListTemp, userId);

            boltService.saveNewBolt(userId, request);

            return "redirect:/all_bolt";
        }

        @GetMapping("/all_bolt/{id}")
        public String deleteBolt(@PathVariable("id") int boltId, Model model){

       try {
           boltService.deletebolt(boltId);
       }
       catch (FormatException formatException){
           model.addAttribute("error", formatException.getMessage());
           log.error("Ошибка при удалении болта id = {}", boltId);

           return "error_page.html";
       }

        return "redirect:/all_bolt";

        }
}
