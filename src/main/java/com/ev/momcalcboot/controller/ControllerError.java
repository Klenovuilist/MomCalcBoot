package com.ev.momcalcboot.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class ControllerError {


    @GetMapping("/error")
    public String errorPage(Model model, Exception e) {


        model.addAttribute("error", e.getMessage());

        return "error_page.html";
    }
}