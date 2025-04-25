package com.ev.momcalcboot.dao.impl;

import com.ev.momcalcboot.Entity.UserEntity;
import com.ev.momcalcboot.dao.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.hibernate.Session;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Data
public class UserDaoImpl implements UserDao {


    private final EntityManager entityManager;

    @Override
    public void saveUser(UserEntity userEntity) {

        Session session = entityManager.unwrap(Session.class);
        session.save(userEntity);
    }

    @Override
    public List<UserEntity> getUser() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from UserEntity", UserEntity.class).getResultList();
    }

    @Override
    public UserEntity getUserByNameByPassword(String userName, String passwordUser) {

        Session session = entityManager.unwrap(Session.class);

        Query query = session.createQuery("from UserEntity where userName = :userName " +
                "and passwordUser = :passwordUser", UserEntity.class);
        query.setParameter("userName", userName);
        query.setParameter("passwordUser", passwordUser);

        return (UserEntity) query.getSingleResult();
    }

    @Override
    public UserEntity getUserByName(String name) {

        try {
            Session session = entityManager.unwrap(Session.class);
            Query query = session.createQuery("from UserEntity where userName = :userName", UserEntity.class);
            query.setParameter("userName", name);

            return (UserEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }


    @Override
    public UserEntity getUserById(int id) {

        Session session = entityManager.unwrap(Session.class);

        return session.get(UserEntity.class, id);
    }

    @Override
    public boolean isUserWihtName(String name) {

        Session session = entityManager.unwrap(Session.class);
//     getUserByName(name);
        return getUserByName(name) != null;

    }

    @Override
    public List<UserEntity> getAllUsers() {

        Session session = entityManager.unwrap(Session.class);

        Query query = session.createQuery("from UserEntity", UserEntity.class);

        return (List<UserEntity>) query.getResultList();
    }
}
