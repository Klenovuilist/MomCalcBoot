package com.ev.momcalcboot.dao.impl;

import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.dao.MaterialsDao;
import jakarta.persistence.EntityManager;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.hibernate.LockMode;
import org.hibernate.Session;

import org.hibernate.annotations.OptimisticLock;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaterialDaoImpl implements MaterialsDao {

    @Autowired
    private EntityManager entityManager;


    public MaterialDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;

    }

    @Transactional
        public int materals_dbSize() {

        Session session = entityManager.unwrap(Session.class);
        List<MaterialsEntity> materals_entity = session.createQuery("from MaterialsEntity ", MaterialsEntity.class)
                .getResultList();
        return materals_entity.size();
    }

    @Transactional
    @Override
    public List<MaterialsEntity> getMateralsEntity() {
        Session session = entityManager.unwrap(Session.class);

        return session.createQuery("from MaterialsEntity", MaterialsEntity.class).getResultList();

    }

    @Transactional()
    @Override
    public void save(MaterialsEntity materialsEntity) {
        Session session = entityManager.unwrap(Session.class);
        session.get(MaterialsEntity.class, 1, LockMode.OPTIMISTIC_FORCE_INCREMENT);
        session.save(materialsEntity);
    }

    @Transactional

    @Override
    public MaterialsEntity getMaterialsById(int MaterialId) {
        Session session = entityManager.unwrap(Session.class);

        Query query = session.createQuery("from MaterialsEntity m left join fetch m.momentsEntity r left join fetch r.thread" +
                " where m.id = :id", MaterialsEntity.class);
        query.setParameter("id", MaterialId);
        return (MaterialsEntity) query.getSingleResult();


//        return session.get(Materals_db.class, MaterialId);
    }
@Transactional
    @Override
    public List getMaterialsByUserId(int UserId) {

    Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("from MaterialsEntity where user.id = :idUser", MaterialsEntity.class).setLockMode(LockModeType.OPTIMISTIC);
        query.setParameter("idUser", UserId);

        return query.getResultList();
    }
    @Transactional
    @Override
    public void updateMaterial(MaterialsEntity materals_entity) {
        Session session = entityManager.unwrap(Session.class);
        session.update(materals_entity);
    }
    @Transactional
    @Override
    public void materialRemove(MaterialsEntity materals_entity) {
        Session session = entityManager.unwrap(Session.class);
        session.remove(materals_entity);

    }

}
