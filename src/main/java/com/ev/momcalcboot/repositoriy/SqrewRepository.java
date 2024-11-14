package com.ev.momcalcboot.repositoriy;

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

    @Query("select sqrew from SqrewEntity as sqrew left join fetch sqrew.user where sqrew.user.id = :userId")
    List<SqrewEntity> findSqrewByUserId(@Param("userId") int userId);

}
