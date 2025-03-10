package com.ev.momcalcboot.controller;


import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.Entity.SqrewEntity;
import com.ev.momcalcboot.Entity.UserEntity;
import com.ev.momcalcboot.dao.UserDao;
import com.ev.momcalcboot.dto.UserDto;
import com.ev.momcalcboot.dtoService.UserDtoService;

import com.ev.momcalcboot.repositoriy.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Data
@Tag(name = "Тестовый рест контроллер")
@AllArgsConstructor
@RestController
public class RestControllerTest {

    private final UserDtoService userDto;

    private final BoltDaoRepository boltDaoRepository;

    private final SqrewDaoRepository sqrewDaoRepository;

    private  final UserDaoRepository userDaoRepository;

    private final TestRepository testRepository;


    @GetMapping(value = "/allusers"/*, consumes = MediaType.APPLICATION_JSON_VALUE*/)

    public List<UserEntity> allUsers(){

        LinkedList <String> queue = new LinkedList<>();

        ArrayList<String> list = new ArrayList();


        return userDaoRepository.getAllUser();

    }



    @GetMapping("/{id}")
    public String testView(@PathVariable("id") int id){

//        boltDaoRepository.getBoltById(1);
//        List<BoltEntity> boltEntities = boltDaoRepository.getBoltByUserId(1);
//        List<SqrewEntity> sqrewEntities = sqrewDaoRepository.getSqrewByUserId(2);

            return "test_page.html id = " + id;
    }

    @GetMapping("/test")
    public UserEntity testQuery(){

        return testRepository.getUsersByIdTest(2);

    }

    }

