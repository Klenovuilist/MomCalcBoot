package com.ev.momcalcboot.dao.impl;

import com.ev.momcalcboot.Entity.MomentsEntity;
import com.ev.momcalcboot.dao.MomentsDao;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MomentsDaoImpl implements MomentsDao {

    @Autowired
    private EntityManager entityManager;

    public MomentsDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<MomentsEntity> getMoments_db() {
        Session session = entityManager.unwrap(Session.class);

       return session.createQuery("from MomentsEntity mom join fetch mom.thread", MomentsEntity.class).getResultList();
  }

    @Override
    public void saveAll(MomentsEntity moments_dbs) {
        Session session = entityManager.unwrap(Session.class);
        session.save(moments_dbs);
      }

    @Override
    public void updateMoment(MomentsEntity moments_entity) {
        Session session = entityManager.unwrap(Session.class);
        session.update(moments_entity);
    }

    @Override
    public List<MomentsEntity> momentsByIdMaterial(int idMaterial) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("from MomentsEntity mom where mom.materialsEntity.id = :id", MomentsEntity.class);

        query.setParameter("id", idMaterial);

        List<MomentsEntity> moments_entities = (List<MomentsEntity>)query.getResultList();
        return moments_entities;
    }
    @Override
    public void momentRemove(MomentsEntity moments_entity) {
    Session session = entityManager.unwrap(Session.class);
        session.remove(moments_entity);
    }

}
