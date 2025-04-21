package com.ev.momcalcboot.service.internal;


import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.Entity.UserEntity;
import com.ev.momcalcboot.dao.MaterialsDao;
import com.ev.momcalcboot.dao.MomentsDao;
import com.ev.momcalcboot.dao.ThreadDao;
import com.ev.momcalcboot.dao.UserDao;
import com.ev.momcalcboot.repositoriy.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class FillDataBase {

    private final BoltRepository boltRepository;

    private final SqrewRepository sqrewRepository;

    private final UserRepository userRepository;

    private final MaterialsDao materialsDao;

    private final MomentsDao momentsDao;

    private final ThreadDao threadDao;

    private final UserDao userDao;

 public void fillData(){


 }

}
