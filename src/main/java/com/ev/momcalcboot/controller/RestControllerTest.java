package com.ev.momcalcboot.controller;


import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.Entity.SqrewEntity;
import com.ev.momcalcboot.dto.UserDto;
import com.ev.momcalcboot.dtoService.UserDtoService;

import com.ev.momcalcboot.repositoriy.BoltDaoRepository;
import com.ev.momcalcboot.repositoriy.SqrewDaoRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@Data
@AllArgsConstructor
@RestController
public class RestControllerTest {

    private final UserDtoService userDto;

    private final BoltDaoRepository boltDaoRepository;

    private final SqrewDaoRepository sqrewDaoRepository;

    @GetMapping("/allusers")
    public List<UserDto> allUsers(){

        return userDto.getAllUserDto();
    }

    @GetMapping("/")
    public String testView(){

        boltDaoRepository.getBoltById(1);
        List<BoltEntity> boltEntities = boltDaoRepository.getBoltByUserId(1);
        List<SqrewEntity> sqrewEntities = sqrewDaoRepository.getSqrewByUserId(2);

            return "test_page.html";
    }

    }

