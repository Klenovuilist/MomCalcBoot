package com.ev.momcalcboot.dao;



import com.ev.momcalcboot.Entity.MaterialsEntity;

import java.util.List;

public interface MaterialsDao {

    public List<MaterialsEntity> getMateralsEntity();

    public void save(MaterialsEntity materals_entity);

    public MaterialsEntity getMaterialsById(int id);

    public List<MaterialsEntity> getMaterialsByUserId(int UserId);

    public void updateMaterial(MaterialsEntity materals_entity);

    public void materialRemove (MaterialsEntity materals_entity);

}


