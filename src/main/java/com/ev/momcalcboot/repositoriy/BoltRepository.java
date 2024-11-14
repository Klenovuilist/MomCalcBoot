package com.ev.momcalcboot.repositoriy;

import com.ev.momcalcboot.Entity.BoltEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoltRepository extends JpaRepository<BoltEntity, Integer> {
   
//      List<BoltEntity> findBoltEntityByUserId(int userId);


      @Query("" +
//              "select bolt " +
              "FROM BoltEntity as bolt right join fetch bolt.user as u where u.id = :userId")
      List<BoltEntity> findBoltsByUserId(@Param("userId") int userId);


//"from MaterialsEntity m left join fetch m.momentsEntity r left join fetch r.thread" +
//        " where m.id = :id", MaterialsEntity .class);


}
