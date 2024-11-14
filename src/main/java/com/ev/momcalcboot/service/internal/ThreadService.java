package com.ev.momcalcboot.service.internal;

import com.ev.momcalcboot.Entity.ThreadEntity;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class ThreadService {

    /**
     * ��������� ������ �� ����� �� ����������  � ������� �������������
     */
//    public ThreadEntity getThreadByRequestParam(HttpServletRequest request, ThreadEntity currentThread){
//
//        ThreadEntity thread = new ThreadEntity();
//
//        return ThreadEntity.builder().
//                StepThread_mm((Objects.nonNull((request.getParameter("stepThread_mm")))) ?
//                        Double.parseDouble((request.getParameter("stepThread_mm"))) :
//                        currentThread.getStepThread_mm()).
//                dBolt_mm(Objects.nonNull(request.getParameter("diametrThread_mm")) ?
//                        Double.parseDouble(request.getParameter("diametrThread_mm")) :
//                        currentThread.getDBolt_mm()).
//                thread(Objects.nonNull(request.getParameter("thread")) ?
//                        (request.getParameter("thread")) :
//                        currentThread.getThread()).
//                dHead_mm(Objects.nonNull(request.getParameter("diametrHead_mm")) ?
//                        Double.parseDouble(request.getParameter("diametrHead_mm")) :
//                        currentThread.getDHead_mm()).
//                dhole_mm(Objects.nonNull(request.getParameter("diametrHole_mm")) ?
//                        Double.parseDouble(request.getParameter("diametrHole_mm")) :
//                        currentThread.getDhole_mm()).
//                dMidlethread_mm(Objects.nonNull(request.getParameter("middleDiamThread_mm")) ?
//                        Double.parseDouble(request.getParameter("middleDiamThread_mm")) :
//                        currentThread.getDMidlethread_mm()).
//                id(currentThread.getId()).
//                build();
//
//    }

    public ThreadEntity getThreadByRequestParam(Map<String, String> paramFromForm, ThreadEntity currentThread){

        ThreadEntity thread = new ThreadEntity();

        return ThreadEntity.builder().
                StepThread_mm((Objects.nonNull((paramFromForm.get("stepThread_mm")))) ?
                        Double.parseDouble((paramFromForm.get("stepThread_mm"))) :
                        currentThread.getStepThread_mm()).
                dBolt_mm(Objects.nonNull(paramFromForm.get("diametrThread_mm")) ?
                        Double.parseDouble(paramFromForm.get("diametrThread_mm")) :
                        currentThread.getDBolt_mm()).
                thread(Objects.nonNull(paramFromForm.get("threadName")) ?
                        (paramFromForm.get("threadName")) :
                        currentThread.getThread()).
                dHead_mm(Objects.nonNull(paramFromForm.get("diametrHead_mm")) ?
                        Double.parseDouble(paramFromForm.get("diametrHead_mm")) :
                        currentThread.getDHead_mm()).
                dhole_mm(Objects.nonNull(paramFromForm.get("diametrHole_mm")) ?
                        Double.parseDouble(paramFromForm.get("diametrHole_mm")) :
                        currentThread.getDhole_mm()).
                dMidlethread_mm(Objects.nonNull(paramFromForm.get("middleDiamThread_mm")) ?
                        Double.parseDouble(paramFromForm.get("middleDiamThread_mm")) :
                        currentThread.getDMidlethread_mm()).
                id(currentThread.getId()).
                build();

    }


}
