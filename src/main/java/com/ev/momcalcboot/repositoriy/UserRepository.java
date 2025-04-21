package com.ev.momcalcboot.repositoriy;

import com.ev.momcalcboot.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.sql.ResultSet;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    @Query(value = "select user_name, bolt_name from users" +
            "     left join bolt_entity on users.id = id_user" +
            "        where users.id = :userId or users.id = 1"  , nativeQuery = true)
     List<Object[]> getUserEntitiesByBoltIdTest(@Param("userId") int userId);

}
