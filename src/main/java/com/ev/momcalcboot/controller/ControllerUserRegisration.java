package com.ev.momcalcboot.controller;

import com.ev.momcalcboot.Entity.UserEntity;
import com.ev.momcalcboot.dao.UserDao;
import com.ev.momcalcboot.dto.UserDto;
import com.ev.momcalcboot.service.internal.CookService;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@Data
@Controller
public class ControllerUserRegisration {

    private final UserDao userDao;

    private final CookService cookService;

@GetMapping("/user_registration")
    public String registrationUser(HttpServletRequest request, Model model){

    model.addAttribute("userInfo", "enter your username and password");

    Cookie[] cookies = request.getCookies();
    for (Cookie cookie: cookies){
        if((cookie.getName()).equals("userInfo")){
            if (cookie.getValue().equals("0")) {
                model.addAttribute("userInfo", "User not found");
            }
            break;
        }

    }
        return "users_regisration.html";
    }

    @GetMapping("/user_registrationEnter")
    public String registrationUserEnter(HttpServletRequest request, HttpServletResponse response){

    String username = request.getParameter("nameUser");
    String passwordUser = request.getParameter("passwordUser");
    try {
        UserEntity userEntity = userDao.getUserByNameByPassword(username, passwordUser);

        cookService.setCookiesForUserResponse(response
                , String.valueOf(userEntity.getId())
                , userEntity.getUserName()
                , userEntity.getRoleUser());

//        Cookie cookieUser = new Cookie("userId", String.valueOf(user.getId()));
//        response.addCookie(cookieUser);
//
//        response.addCookie(new Cookie("userName", user.getUserName()));
//
//        response.addCookie(new Cookie("userRole", user.getRoleUser()));

        Cookie cookie = new Cookie("userInfo", "0");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/moment_page_1";

    }
    catch (NoResultException e){
        Cookie cookie = new Cookie("userInfo", "0");

        response.addCookie(cookie);
        return "redirect:/user_registration";

        }
    }
    @GetMapping("/users_create")
    public String userCreate(){


        return "users_create.html";

    }

    @GetMapping("/users_create_enter")
    public String userCreateEnter(Model model, HttpServletRequest request, HttpServletResponse response){

    request.getParameter("nameUser");
    request.getParameter("passwordUser1");
    request.getParameter("passwordUser2");

    if (request.getParameter("nameUser") == null
            || request.getParameter("passwordUser1") == null
            || request.getParameter("passwordUser2") == null ) {

        return "redirect:/users_create";
    }

        if(! request.getParameter("passwordUser1").equals(request.getParameter("passwordUser2"))){
            return "redirect:/users_create";
        }

    if(userDao.isUserWihtName(String.valueOf(request.getParameter("nameUser")))){
        return "redirect:/users_create";
    }


        LocalDate localDate = LocalDate.now();

//        Date data = new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        UserEntity userEntity = UserEntity.builder()
                .userName(request.getParameter("nameUser"))
                .passwordUser(request.getParameter("passwordUser1"))
                .roleUser("engin")
                .build();
        userDao.saveUser(userEntity);

        cookService.setCookiesForUserResponse(response, String.valueOf(userEntity.getId()), userEntity.getUserName(), userEntity.getRoleUser());

        return "redirect:/moment_page_1";

    }

    /**
     * Запрос с json с теле запроса от клиента
     * @param userDto
     * @param model
     * @return
     */

    @PostMapping("/users")
    public String getUser(@RequestBody List<UserDto> userDto, Model model){

        model.addAttribute("nameUserDto",userDto.get(1).getUserName());

        model.addAttribute("dataUserDto",userDto.get(1).getDataUser());

        return "adminPage.html";
    }
}
