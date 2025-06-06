package com.ev.momcalcboot.controller;

import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.enums.CookiesParametr;
import com.ev.momcalcboot.exceptions.FormatException;
import com.ev.momcalcboot.repositoriy.UserDaoRepository;
import com.ev.momcalcboot.service.internal.BoltService;
import com.ev.momcalcboot.service.internal.CookService;
import jakarta.servlet.http.Cookie;
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
import java.util.Objects;

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


    private List<BoltEntity> boltListTemp;

    @GetMapping("/all_bolt")
    public String viewAllBolt(Model model, HttpServletRequest request) {
        log.info("Открытие страницы с болтами");

        /**
         * получение польз. из куков
         */

        Cookie cookieUserId = cookService.findCookByName(request, CookiesParametr.USERID.getParam());
        // если пользователь не получен - вернуть на регистрацию
        if (Objects.isNull(cookieUserId)) {
            return "redirect:/user_registration";
        }

        Integer userId = Integer.parseInt(cookieUserId.getValue());
        model.addAttribute("user", userDaoRepository.getUserById(userId));

        Cookie cookieUserName = cookService.findCookByName(request, CookiesParametr.USERNAME.getParam());
        String userName = cookieUserName.getValue();

        log.info("Получен пользователь из куков id = {}, имя: {}", userId, userName);

        /**
         * Список болтов админа (id =1)
         */

        model.addAttribute("boltsAdmin", boltService.boltsAdmin());

        /**
         * Список болтов польз по его id
         */
        boltListTemp.clear();
        boltListTemp.addAll(boltService.getBoltByUserId(userId));
        model.addAttribute("boltsUser", boltListTemp);

        return "all_bolt.html";
    }


    /**
     * Сохранение всех значений болтов по данным из формы
     */
    @PostMapping("/all_bolt")
    public String saveChangeBolt(HttpServletRequest request) {

        Integer userId = Integer.parseInt(cookService.findCookByName(request, CookiesParametr.USERID.getParam()).getValue());

        boltService.saveBoltsByParametrForm(request, boltListTemp, userId);
        boltService.saveNewBolt(userId, request);

        return "redirect:/all_bolt";
    }

    /**
     * Удаление болтов по id
     */
    @GetMapping("/all_bolt/{id}")
    public String deleteBolt(@PathVariable("id") int boltId, Model model) {

        try {
            boltService.deletebolt(boltId);
            log.info("Удален болт id = {}", boltId);
        } catch (FormatException formatException) {
            model.addAttribute("error", formatException.getMessage());
            log.error("Ошибка при удалении болта id = {}", boltId);

            return "error_page.html";
        }

        return "redirect:/all_bolt";

    }
}
