package com.ev.momcalcboot.repositoriy;

import com.ev.momcalcboot.Entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@Repository
@Transactional
public class TestRepository {

    private final UserRepository userRepository;

    private  final  BoltRepository boltRepository;


    public UserEntity getUsersByIdTest(int userId){

        List<Object[]> users =  userRepository.getUserEntitiesByBoltIdTest(userId);

        users.forEach(user -> System.out.println(user[0] +  "  " + user[1])

     );

        return null;
    }

}

