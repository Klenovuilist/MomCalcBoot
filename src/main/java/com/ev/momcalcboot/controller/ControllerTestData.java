package com.ev.momcalcboot.controller;


import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.Entity.SqrewEntity;
import com.ev.momcalcboot.Entity.ThreadEntity;
import com.ev.momcalcboot.Entity.UserEntity;
import com.ev.momcalcboot.dao.ThreadDao;
import com.ev.momcalcboot.dto.ThreadDTO;
import com.ev.momcalcboot.dto.UserDto;
import com.ev.momcalcboot.dtoService.ThreadDtoService;
import com.ev.momcalcboot.dtoService.UserDtoService;
import com.ev.momcalcboot.repositoriy.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class ControllerTestData {

    private final TestDatalLoad testDatalLoad;

    private final UserDtoService userDtoService;

    private final BoltDaoRepository boltDaoRepository;

    private final SqrewDaoRepository sqrewDaoRepository;

    private final ThreadDtoService threadDtoService;

    private List<BoltEntity> boltEntities = new ArrayList<>();
    private Map<String, Boolean> resultLoadMap = new HashMap<>();

    private List<SqrewEntity> sqrewEntities = new ArrayList<>();

    private List<UserDto> userDtoList = new ArrayList<>();

    private List<ThreadDTO> threadDTOList = new ArrayList<>();

    private String sql = "no sql request";

    int i = 0;

    @GetMapping("/test_data")
    public String getTestPage(Model model) throws InterruptedException {

//    Map<String,Boolean> resultLoadMap = testDatalLoad.loadBdTestData();
        if (resultLoadMap.isEmpty()) {
            resultLoadMap = testDatalLoad.loadBdTestData(false);
        }
        model.addAttribute("mapLoad", resultLoadMap);

        if (! boltEntities.isEmpty()) {
            model.addAttribute("allBolts", boltEntities);

        }

        if (!sqrewEntities.isEmpty()) {
            model.addAttribute("allSqrews", sqrewEntities);

        }

        if (!userDtoList.isEmpty()) {
            model.addAttribute("allUsers", userDtoList);

        }

        if (! threadDTOList.isEmpty()){
            model.addAttribute("allThreads", threadDTOList);

        }
        model.addAttribute("sql1", sql);

//        i++;
//        if (i % 2 > 0){
//            Thread.sleep(10_000);//
//        }//
//        System.out.println("cont = " + i + "  Thread = " + Thread.currentThread().getName());

        return "test.html";
    }

    @GetMapping("/test_data/{load}")
    public String loadData(@PathVariable("load") String load, Model model) {

        if (load.equals("load_data")) {
            resultLoadMap = testDatalLoad.loadBdTestData(true);
        }

        if (load.equals("get_data")) {

            boltEntities.clear();
            sqrewEntities.clear();
            userDtoList.clear();
            threadDTOList.clear();

            boltEntities.addAll(boltDaoRepository.getAllBolts());
            sqrewEntities.addAll(sqrewDaoRepository.getAllsqrews());
            userDtoList.addAll(userDtoService.getAllUserDto());
            threadDTOList.addAll(threadDtoService.getAllThreadDto());
        }



        return "redirect:/test_data";

    }

    @PostMapping("/test_data")
    public String sqlRequest(HttpServletRequest request){

       if (testDatalLoad.loadData(request.getParameter("sql"))){
           sql = request.getParameter("sql");
       }

        return "redirect:/test_data";
    }
}
