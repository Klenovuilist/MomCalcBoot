package com.ev.momcalcboot;

import com.ev.momcalcboot.repositoriy.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication

public class MomCalcBootApplication {


    public static void main(String[] args) {

        ConfigurableApplicationContext contex = SpringApplication.run(MomCalcBootApplication.class, args);

//        System.out.println("Загрузка Application");


    }

}
