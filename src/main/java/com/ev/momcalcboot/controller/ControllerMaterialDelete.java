package com.ev.momcalcboot.controller;

import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.Entity.MomentsEntity;
import com.ev.momcalcboot.dao.MaterialsDao;
import com.ev.momcalcboot.dao.MomentsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ControllerMaterialDelete {

    @Autowired
    MaterialsDao materialsDao;
    @Autowired
    MomentsDao momentsDao;

    @GetMapping("/materials_delete/{id}")
    public String materialsDelete(@PathVariable("id") int id) {

        MaterialsEntity materalForDelete = materialsDao.getMaterialsById(id);

        List<MomentsEntity> momentsForDelete = materalForDelete.getMomentsEntity();

        momentsForDelete.forEach(m -> momentsDao.momentRemove(m));

        materalForDelete.getMomentsEntity().clear();

        materialsDao.materialRemove(materalForDelete);

        return "redirect:/";
    }
}
