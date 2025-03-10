package com.ev.momcalcboot.repositoriy;

import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.controller.ControllerError;
import com.ev.momcalcboot.exceptions.FormatException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Objects;


@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
@AllArgsConstructor
public class BoltDaoRepository {

    private final BoltRepository boltRepository;

    private  final ControllerError controllerError;

    /**
   *
     */
    public BoltEntity getBoltById(int boltId){

       return boltRepository.findById(boltId).orElse(null);
    }

    public List<BoltEntity> getBoltByUserId(int id){

        return boltRepository.findBoltsByUserId(id) ;
    }

    public void saveBolt(BoltEntity bolt){

      boltRepository.save(bolt);
        }

@Transactional()
        public boolean deleteBolt(int boltId){

        boolean result = false;

        try {
            BoltEntity bolt = boltRepository.findById(boltId).orElse(null);

            if(Objects.nonNull(bolt)){

                boltRepository.delete(bolt);
                result = true;
            }

        }
        catch (RuntimeException e){

            throw new FormatException("не удалось удалить материал");
                    }
            return result;
        }
        public List<BoltEntity> getAdminBolts(){

        return boltRepository.findAdminBolts();
        }

        public List<BoltEntity> getAllBolts(){
        return boltRepository.findAll();

        }
}
