package com.ev.momcalcboot.controller;

import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.Entity.MomentsEntity;
import com.ev.momcalcboot.Entity.SqrewEntity;
import com.ev.momcalcboot.Entity.UserEntity;
import com.ev.momcalcboot.dao.MaterialsDao;
import com.ev.momcalcboot.dao.MomentsDao;
import com.ev.momcalcboot.dto.UserDto;
import com.ev.momcalcboot.dtoService.MaterialDtoService;
import com.ev.momcalcboot.dtoService.UserDtoService;
import com.ev.momcalcboot.exceptions.FormatException;
import com.ev.momcalcboot.service.internal.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@Data
@Tag(name = "Admin")

public class ControllerAdmin {

private final UserDtoService userDtoService;

private final MaterialDtoService materialDtoService;

private final AdminService adminService;

private final MaterialsDao materialsDao;

private  final MomentsDao momentsDao;


    @GetMapping("/admin")
    public String adminPage(Model model){

        List<UserDto> allUsersDto = userDtoService.getAllUserDto();

        UserEntity userEntity = UserEntity.builder()
                .userName(allUsersDto.get(0).getUserName())
                .dataUser(allUsersDto.get(0).getDataUser())
                .id(allUsersDto.get(0).getId())
                .passwordUser(allUsersDto.get(0).getPasswordUser())
                .roleUser(allUsersDto.get(0).getRoleUser())
                .build();

        model.addAttribute("userIndex0", userEntity);

        model.addAttribute("allUsers", allUsersDto);


        return "adminPage.html";

    }
    @GetMapping("/user/{userId}")
    public String userDetail(@PathVariable int userId, Model model){

        UserDto userDto = userDtoService.getUserDtoByIdWithMaterial(userId);

//        List< MateralsDto> materalsDto = materialDtoService.getMaterialDtoByUserId(id);

        List<BoltEntity> bolts = adminService.getBoltByUserId(userId);
        List<SqrewEntity> sqrews = adminService.getSqrewByUserId(userId);


        model.addAttribute("userDto", userDto);

        model.addAttribute("bolts", bolts);

        model.addAttribute("sqrews", sqrews);
        return "user_info.html";
    }
    @PostMapping("/user_save_changes")
    public String saveUserChangeAdmin(HttpServletRequest request){

       adminService.saveMaterialRequestAdmin(request, Integer.parseInt(request.getParameter("userId")));

       adminService.saveUserRequestAdmin(request, Integer.parseInt(request.getParameter("userId")));

        return "redirect:/admin";

    }

    /**
     * Удаление материала по id
     */
    @GetMapping("material_delete/{id}")
    public String materialDeleteAdmin(@PathVariable("id") int id){

        List<MomentsEntity> moments_entities = momentsDao.momentsByIdMaterial(id);

        for (MomentsEntity moment: moments_entities){

            momentsDao.momentRemove(moment);
        }

        MaterialsEntity materals_entity = materialsDao.getMaterialsById(id);

        materals_entity.getMomentsEntity().clear();

        materialsDao.materialRemove(materals_entity);


        return "redirect:/user_info.html";
    }

        @GetMapping("/bolt_delete/{id}/{userId}")
        public String deleteBoltById(@PathVariable("id") int boltId, @PathVariable("userId") int userId, Model model ){
        try {
//            int userId = Integer.parseInt(request.getParameter("userId"));
            adminService.deleteBoltById(boltId);

            return "redirect:/user/" + userId;
        }

        catch (FormatException f){
            model.addAttribute("error", f.getMessage());
                return "error_page.html";
        }
            }

    /**
     *
     * @param id
     * @param userId
     * @param model
     * @return
     * Удалить материал по юзер id и id материала
     */
    @GetMapping("/sqrew_delete/{id}/{userId}")
    public String deleteSqrewById(@PathVariable("id") int id, @PathVariable("userId") int userId, Model model){
        try {
            adminService.deleteSqrewById(id);
            return "redirect:/user/" + userId;
        }
        catch (FormatException f){

            model.addAttribute("error", f.getMessage());
            return "error_page.html";
        }
        }

}
