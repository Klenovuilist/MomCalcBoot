package com.ev.momcalcboot.dtoService;

import com.ev.momcalcboot.dao.ThreadDao;
import com.ev.momcalcboot.dto.ThreadDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThreadDtoService {

    private final ThreadDao threadDao;

    public List<ThreadDTO> getAllThreadDto(){

        List<ThreadDTO> threadDTOList = new ArrayList<>();

        threadDao.getThread().forEach(threadEntity -> {

            List<String> paramThread = new ArrayList<>();

            paramThread.add(String.valueOf(threadEntity.getId()));
            paramThread.add(String.valueOf(threadEntity.getThread()));
            paramThread.add(String.valueOf(threadEntity.getDBolt_mm()));
            paramThread.add(String.valueOf(threadEntity.getStepThread_mm()));
            paramThread.add(String.valueOf(threadEntity.getDHead_mm()));
            paramThread.add(String.valueOf(threadEntity.getDhole_mm()));
            paramThread.add(String.valueOf(threadEntity.getDMidlethread_mm()));

            threadDTOList.add(ThreadDTO.builder()
                    .parametrThread(paramThread)
                    .threadName(threadEntity.getThread())
                    .build());

        });
        return threadDTOList;
    }
}
