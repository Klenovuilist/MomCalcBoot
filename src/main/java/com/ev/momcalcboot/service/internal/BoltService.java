package com.ev.momcalcboot.service.internal;

import com.ev.momcalcboot.Entity.BoltEntity;
import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.Entity.UserEntity;
import com.ev.momcalcboot.dao.impl.UserDaoImpl;
import com.ev.momcalcboot.repositoriy.BoltDaoRepository;
import com.ev.momcalcboot.repositoriy.UserDaoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.ev.momcalcboot.service.internal.ParserNumber.toDouble;
import static com.ev.momcalcboot.service.internal.ParserNumber.toInt;


@Service
@AllArgsConstructor
public class BoltService {

    @Autowired
    private final BoltDaoRepository boltDaoRepository;

    @Autowired
    private final UserDaoRepository userDaoRepository;

    @Autowired
    private final UserDaoImpl userDao;



    /**
     * Получение списка болтов по id user
     * @param userId
     * @return
     */
    public List<BoltEntity> boltsByUserId(int userId){

        return boltDaoRepository.getBoltByUserId(userId);
    }

    /**
     * Получение списка болтов Admin
     * @return
     */
    public List<BoltEntity> boltsAdmin(){

        return  boltDaoRepository.getAdminBolts();
    }

    /**
     *  полный список болтов от юзера и админа
     */
    public List<BoltEntity> allBoltAdminUser(int userId, List<BoltEntity> boltUser, List<BoltEntity> boltAdmin){

        List<BoltEntity> allList = new ArrayList<>();

        if (! boltUser.isEmpty() && userId > 1){
            allList.addAll(boltUser);
        }

        if (! boltAdmin.isEmpty()){
            allList.addAll(boltAdmin);
        }
        return allList;
    }

    /**
     *
     * @param request
     * @return
     */

    public BoltEntity getBoltByRequestParam(HttpServletRequest request) {

        var bolt = new BoltEntity();


        /**
         *
         */
        String limit = request.getParameter("limitBolt");

        if (Strings.isNotBlank(limit)) {

            bolt.setLimit(Integer.parseInt(limit));
        } else {
            bolt.setLimit(0);
        }

        /**
         *
         */
        String name = request.getParameter("name");

        if (Strings.isNotBlank(name)) {

            bolt.setName(name);
        } else {
            bolt.setName("No name");
        }

        /**
         *
         */
        String comments = request.getParameter("comments");

        if (Strings.isNotBlank(comments)) {

            bolt.setComment(comments);
        } else {
            bolt.setComment(null);
        }


        /**
         *
         */
        String classBolt = request.getParameter("classBolt");

        if (Strings.isNotBlank(classBolt)) {

            bolt.setClassBolt(toDouble(classBolt));
        } else {
            bolt.setClassBolt(0d);
        }

        /**
         *
         */
        bolt.setDataCreate(java.time.LocalDateTime.now());

        return bolt;
    }

    /**
     *
     * @param request
     * @return
     */
    public BoltEntity getChangeBoltWithRequestParam(HttpServletRequest request) {

        var bolt = boltDaoRepository.getBoltById(Integer.parseInt(request.getParameter("boltId")));

        if (Objects.nonNull(bolt)){
            /**
             *
             */
            String limit = request.getParameter("limitBolt");

            if (Strings.isNotBlank(limit)) {
                bolt.setLimit(Integer.parseInt(limit));
            }


            /**
             *
             */
            String name = request.getParameter("name");

            if (Strings.isNotBlank(name)) {
                bolt.setName(name);
            }

            /**
             *
             */
            String comments = request.getParameter("comments");

            if (Strings.isNotBlank(comments)) {
                bolt.setComment(comments);
            }


            /**
             *
             */
            String classBolt = request.getParameter("classBolt");

            if (Strings.isNotBlank(classBolt)) {
                bolt.setClassBolt(toDouble(classBolt));
            }

            /**
             *
             */
            bolt.setDataCreate(java.time.LocalDateTime.now());

            return bolt;
        }

      return new BoltEntity();
    }

    /**
     *111
     */
    public BoltEntity getBoltByRequestBoltId(HttpServletRequest request) {

         return boltDaoRepository.getBoltById(Integer.parseInt(request.getParameter("boltId")));

    }

    /**

     */
    public BoltEntity getBoltByRequestBoltIdOrParam(List<BoltEntity> bolts, HttpServletRequest request) {

        return bolts.stream().
                filter(bolt ->
                    String.valueOf(bolt.getId()).equals(request.getParameter("bolt")) ||
                       String.valueOf(bolt.getId()).equals(request.getParameter("bolt_on_main_page"))
                ).
                findAny().orElse(bolts.get(0));

    }

    /**
     * 1111
     */
    public List<BoltEntity> getBoltByUserId(int userId){

            List<BoltEntity> boltEntities = boltDaoRepository.getBoltByUserId(userId);

            if (! boltEntities.isEmpty()){
                DateTimeFormatter dataFormater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

                for (BoltEntity bolt: boltEntities){

                    bolt.setDataCreateParsing(dataFormater.format(bolt.getDataCreate()));
                }
            }



        return boltEntities;
    }


    /**
     *1111
     */
    public boolean saveBoltsByParametrForm(HttpServletRequest request, List<BoltEntity> boltsUser, Integer userId){

         if(Objects.nonNull(userId)){

            UserEntity user =  userDaoRepository.getUserById(userId);

                 boltsUser.forEach(bolt ->{

                     boolean saveKey = false;


                    if (Objects.nonNull(request.getParameter("comments" + bolt.getId())) &&
                            ! request.getParameter("comments" + bolt.getId()).equals(bolt.getComment())){

                        bolt.setComment(request.getParameter("comments" + bolt.getId()));
                        saveKey = true;
                    }


                     if (Strings.isNotBlank(request.getParameter("name" + bolt.getId())) &&
                             ! request.getParameter("name" + bolt.getId()).equals(bolt.getName())) {

                         bolt.setName(request.getParameter("name" + bolt.getId()));
                         saveKey = true;
                     }


                     if (Strings.isNotBlank(request.getParameter("limit" + bolt.getId()))){

                         if (bolt.getLimit() == null) {
                             bolt.setLimit(Integer.parseInt(request.getParameter("limit" + bolt.getId())));
                             saveKey = true;
                         }
                         else if (Integer.parseInt(request.getParameter("limit" + bolt.getId())) != bolt.getLimit()) {
                             bolt.setLimit(Integer.parseInt(request.getParameter("limit" + bolt.getId())));
                             saveKey = true;
                         }

                            }


                     if (Strings.isNotBlank(request.getParameter("classBolt" + bolt.getId()))){

                         if (bolt.getClassBolt() == null) {
                             bolt.setClassBolt(toDouble(request.getParameter("classBolt" + bolt.getId())));
                             saveKey = true;
                         }
                         else if (toDouble(request.getParameter("classBolt" + bolt.getId())) != bolt.getClassBolt()) {
                             bolt.setClassBolt(toDouble(request.getParameter("classBolt" + bolt.getId())));
                             saveKey = true;
                         }
                     }

                     if(saveKey){
                         BoltEntity boltBasa = boltDaoRepository.getBoltById(bolt.getId());

                         boltBasa.setClassBolt(bolt.getClassBolt());
                         boltBasa.setName(bolt.getName());
                         boltBasa.setComment(bolt.getComment());
                         boltBasa.setLimit(bolt.getLimit());
                         boltBasa.setDataCreate(LocalDateTime.now());
                         boltBasa.setUser(user);

                         boltDaoRepository.saveBolt(boltBasa);
                     }
                 });
                 userDao.saveUser(user);
                 return true;
         }
         else {
             return false;
         }
    }

    /**
     * 11
     * */
    public  boolean saveNewBolt(Integer UserId, HttpServletRequest request){
//        "bolt_name_new"
//    "bolt_limit_new"
//    "bolt_classBolt_new"
//    "bolt_dataCreate_new"
//    "bolt.comment_new"
        boolean saveKey = false;

        String name = null;
        Integer limit = 0;
        Double classBolt = 0.0;
        String commets = null;

        if (Strings.isNotBlank(request.getParameter("bolt_name_new"))){

            name = request.getParameter("bolt_name_new");
            saveKey = true;

            if (Strings.isNotBlank(request.getParameter("bolt_limit_new"))) {

                limit = toInt(request.getParameter("bolt_limit_new"));
            }

            if (Strings.isNotBlank(request.getParameter("bolt_classBolt_new"))) {

                classBolt = toDouble(request.getParameter("bolt_classBolt_new"));
            }

            if (Strings.isNotBlank(request.getParameter("bolt_limit_new")) && Strings.isBlank(request.getParameter("bolt_classBolt_new"))){

                classBolt = calcClassBolt(toInt(request.getParameter("bolt_limit_new")));
            }

            if (Strings.isNotBlank(request.getParameter("bolt.comment_new"))){

                commets = request.getParameter("bolt.comment_new");

            }
        }

        if (saveKey && Objects.nonNull(UserId)){
            UserEntity user = userDao.getUserById(UserId);

            boltDaoRepository.saveBolt(
                    BoltEntity.builder()
                            .classBolt(classBolt).
                            comment(commets).
                            dataCreate(LocalDateTime.now()).
                            limit(limit).
                            name(name).
                            user(user).
                            build());

            userDao.saveUser(user);

            return true;

        }
        else {
            return false;
        }
    }

    /**
     * 111
     * */
    public boolean deletebolt(int boltId){

        return boltDaoRepository.deleteBolt(boltId);
    }

    /**
     * 111
     * */
    public double calcClassBolt(int limitBolt) {

        Map<Integer, Double> classGOST = new HashMap<>();

        classGOST.put(160, 0.0);
        classGOST.put(180, 3.6);
        classGOST.put(240, 4.6);
        classGOST.put(320, 4.8);
        classGOST.put(300, 5.6);
        classGOST.put(400, 5.8);
        classGOST.put(360, 6.6);
        classGOST.put(480, 6.8);
        classGOST.put(640, 8.8);
        classGOST.put(720, 9.8);
        classGOST.put(900, 10.9);
        classGOST.put(1080, 12.9);

//        Set<Integer> limitGOST = classGOST.keySet(); // ������ ������

        List<Integer> limitGOSTlist = new ArrayList<>(classGOST.keySet());//������ ������ � ��� ����

        List<Integer> limitGOSTsort = limitGOSTlist.stream().sorted().collect(Collectors.toList()); // ���������� �����

//        limitGOSTlist.add(limitBolt); //���������� � ���� �������� �����
             int count = 0;

        for (; count < limitGOSTsort.size() - 1; count++){
               if( (limitBolt - limitGOSTsort.get(0)) < 0){
                   break;
               }
               if (limitBolt > limitGOSTsort.get(limitGOSTsort.size() - 1)){
                   count = limitGOSTsort.size() - 1;
                   break;
               }
               if ((limitBolt - limitGOSTsort.get(count)) <= (limitGOSTsort.get(count + 1) - limitBolt)){
                   break;
               }
        }

        return classGOST.get(limitGOSTsort.get((count)));
    }


}