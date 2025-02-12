package com.ev.momcalcboot.repositoriy;


import com.ev.momcalcboot.Entity.SqrewEntity;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
@AllArgsConstructor

public class SqrewDaoRepository {

    private final SqrewRepository sqrewRepository;

    /**
     * ????????? sqrew ?? id
     * @param userId
     * @return
     */
    public SqrewEntity getSqrewById(int userId){

        return sqrewRepository.findById(userId).orElse(null);
    }

    /**
     *????????? ?????? ???? ?? id user
     * @param userId
     * @return
     */
    public List<SqrewEntity> getSqrewByUserId(int userId){

        return sqrewRepository.findSqrewEntitiesByUserId(userId);
    }

    /**
     * ?????????? ?????
     * @param sqrew
     */
    public void saveSqrew(SqrewEntity sqrew){
        sqrewRepository.save(sqrew);
    }

    /**
     * ?????????? sqrew ? ??
     * @param sqrew
     */
    public void updateSqrew(SqrewEntity sqrew) {

        saveSqrew(getSqrewById(sqrew.getId()));
    }

    public boolean deleteSqrew(int sqrewId) {

        SqrewEntity sqrew = getSqrewById(sqrewId);

        try {
            sqrewRepository.delete(sqrew);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
