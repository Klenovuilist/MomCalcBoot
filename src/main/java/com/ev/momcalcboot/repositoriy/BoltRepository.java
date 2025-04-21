package com.ev.momcalcboot.repositoriy;

import com.ev.momcalcboot.Entity.BoltEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoltRepository extends JpaRepository<BoltEntity, Integer> {
   
//      List<BoltEntity> findBoltEntityByUserId(int userId);

      /**
       * Получение болтов с пагинацией
       * @param userId

       * @return
       */
      @Query("" +
//              "select bolt " +
              "FROM BoltEntity as bolt  where bolt.user.id = :userId")
      Page<BoltEntity> findBoltsByUserId(@Param("userId") int userId, Pageable pageable);

      /**
       * получение всех болтов всех
       * @param userId
       * @return
       */
      @Query("" +
//              "select bolt " +
              "FROM BoltEntity as bolt  where bolt.user.id = :userId")
      List<BoltEntity> findBoltsByUserId(@Param("userId") int userId);



      List<BoltEntity> findAll();


      @Query(value = "select bolt_entity.id, bolt_name, bolt_limit, id_user, comment, data_create, classbolt from bolt_entity" +
              "     join users on bolt_entity.id_user = users.id" +
              "        where users.role_user = 'admin' "  , nativeQuery = true)
      List<BoltEntity> findAdminBolts();

//      @Query(value = "select user_name, bolt_name from users" +
//              "     left join bolt_entity on users.id = id_user" +
//              "        where users.id = :userId or users.id = 1"  , nativeQuery = true)
//      List<Object[]> getUserEntitiesByBoltIdTest(@Param("userId") int userId);


//"from MaterialsEntity m left join fetch m.momentsEntity r left join fetch r.thread" +
//        " where m.id = :id", MaterialsEntity .class);


}
