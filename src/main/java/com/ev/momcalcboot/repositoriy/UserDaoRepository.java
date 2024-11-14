package com.ev.momcalcboot.repositoriy;

import com.ev.momcalcboot.Entity.UserEntity;
import com.ev.momcalcboot.repositoriy.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class UserDaoRepository {

    private final UserRepository userRepository;

    @Autowired
    public UserDaoRepository(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUser(){
        return userRepository.findAll();
    }

    public UserEntity getUserById(int id){

        return userRepository.findById(id).orElse(null);
    }

    public void updateUser(UserEntity userEntity){
        userRepository.save(userEntity);
    }

}
