package com.ev.momcalcboot.repositoriy;

import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.Entity.SqrewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SqrewRepository extends JpaRepository<SqrewEntity, Integer> {
    @Query("select sqrew from SqrewEntity as sqrew left join fetch sqrew.user where sqrew.user.id = :userId")
    List<SqrewEntity> findSqrewEntitiesByUserId(@Param("userId") int userId);

    @Query("select sqrew from SqrewEntity as sqrew " +
            "left join fetch sqrew.user " +
            "where sqrew.user.id = :userId")
    List<SqrewEntity> findSqrewByUserId(@Param("userId") int userId);

    @Query(value = "select sqrew_entity.id, sqrew_name, sqrew_limit, sqrew_depth, id_user, comment, data_create, classsqrew from sqrew_entity" +
            "     join users on sqrew_entity.id_user = users.id" +
            "        where users.role_user = 'admin' "  , nativeQuery = true)
    List<SqrewEntity> findAdminSqrew();


}
