package com.ev.momcalcboot.dao.impl;

import com.ev.momcalcboot.Entity.ThreadEntity;
import com.ev.momcalcboot.dao.ThreadDao;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ThreadDaoImpl implements ThreadDao {

    @Autowired
    private EntityManager entityManager;

    public ThreadDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<ThreadEntity> getThread() {

        return entityManager.unwrap(Session.class)
                .createQuery("from ThreadEntity", ThreadEntity.class)
                .getResultList();
    }
}


