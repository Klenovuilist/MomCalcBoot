package com.ev.momcalcboot.dao;



import com.ev.momcalcboot.Entity.MomentsEntity;

import java.util.List;

public interface MomentsDao {

    public List<MomentsEntity> getMoments_db();

    public void saveAll(MomentsEntity moments_dbs);

    public  void updateMoment(MomentsEntity moments_entity);

    public  List<MomentsEntity> momentsByIdMaterial(int idMaterial);

    public void momentRemove(MomentsEntity moments_entity);


}
