package com.ev.momcalcboot.dao;

import com.ev.momcalcboot.Entity.UserEntity;

import java.util.List;

public interface UserDao {

    public void saveUser(UserEntity userEntity);

    public List<UserEntity> getUser();

    public UserEntity getUserByNameByPassword(String userName, String password_user);

    public UserEntity getUserByName(String name);

    public UserEntity getUserById(int id);

    public boolean isUserWihtName(String name);

    public List<UserEntity> getAllUsers();
}
