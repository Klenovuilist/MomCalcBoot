package com.ev.momcalcboot.service.internal;

import com.ev.momcalcboot.Entity.SqrewEntity;
import com.ev.momcalcboot.Entity.UserEntity;
import com.ev.momcalcboot.dao.UserDao;
import com.ev.momcalcboot.repositoriy.SqrewDaoRepository;
import com.ev.momcalcboot.repositoriy.UserDaoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class SqrewService {

    private final SqrewDaoRepository sqrewDaoRepository;

    private  final UserDaoRepository userDaoRepository;

    private final UserDao userDao;
    /**
     * 1111
     * @param userId
     * @return
     */
    public List<SqrewEntity> getSqrewsByUserId(int userId){

        return sqrewDaoRepository.getSqrewByUserId(userId);
    }

    /**
     * 111
     * @return
     */
    public List<SqrewEntity> getSqrewsAdmin(){

        return  sqrewDaoRepository.getSqrewByUserId(1);
    }

    /**
     *  111
     */
    public List<SqrewEntity> getAllSqrewAdminUser(int userId, List<SqrewEntity> sqrewUser, List<SqrewEntity> sqrewAdmin){

        List<SqrewEntity> allList = new ArrayList<>();

        if (! sqrewUser.isEmpty() && userId > 1){
            allList.addAll(sqrewUser);
        }

        if (! sqrewAdmin.isEmpty()){
            allList.addAll(sqrewAdmin);
        }
        return allList;
    }

    /**
     * 1111
     * @param request
     * @return
     */
    public SqrewEntity getSqrewByRequestParam(HttpServletRequest request) {

        var sqrew = new SqrewEntity();


        /**
         * ������ ���������
         */
        String limit = request.getParameter("limitSqrew");

        if (Strings.isNotBlank(limit)) {

            sqrew.setLimit(Integer.parseInt(limit));
        } else {
            sqrew.setLimit(0);
        }

        /**
         * ���
         */
        String name = request.getParameter("name");

        if (Strings.isNotBlank(name)) {

            sqrew.setName(name);
        } else {
            sqrew.setName("No name");
        }

        /**
         * �����������
         */
        String comments = request.getParameter("comments");

        if (Strings.isNotBlank(comments)) {

            sqrew.setComment(comments);
        } else {
            sqrew.setComment(null);
        }


        /**
         * ����� ���������
         */
        String classSqrew = request.getParameter("classSqrew");

        if (Strings.isNotBlank(classSqrew)) {

            sqrew.setClassSqrew(Double.parseDouble(classSqrew));
        } else {
            sqrew.setClassSqrew(0d);
        }

        /**
         * ����� ��������
         */
        sqrew.setDataCreate(java.time.LocalDateTime.now());

        /**
         * ������� ������
         */
        String depth = request.getParameter("kDepth");

        if (Strings.isNotBlank(depth)) {

            sqrew.setClassSqrew(Double.parseDouble(depth));
        } else {
            sqrew.setClassSqrew(0.8);
        }


        return sqrew;
    }

    /**
     *11111111
     */
    public SqrewEntity getSqrewByRequestSqrewIdOrParam(HttpServletRequest request) {

        return sqrewDaoRepository.getSqrewById(Integer.parseInt(request.getParameter("sqrewId")));

    }

    /**
     *1111
     */
    public SqrewEntity getSqrewByRequestSqrewIdOrParam(List<SqrewEntity> sqrews, HttpServletRequest request) {

        return sqrews.stream().
                filter(sqrew -> String.valueOf(sqrew.getId()).equals(request.getParameter("sqrew")) ||
                        String.valueOf(sqrew.getId()).equals(request.getParameter("sqrew_on_main_page"))).
                findAny().orElse(sqrews.get(0));

    }



    /**
     *111
     */
    public boolean saveSqrewsByParametrForm(HttpServletRequest request, List<SqrewEntity> sqrewsUser, Integer userId){

        if(Objects.nonNull(userId)){

            UserEntity user =  userDaoRepository.getUserById(userId);

            sqrewsUser.forEach(sqrew ->{

                boolean saveKey = false;


                if (Objects.nonNull(request.getParameter("comments" + sqrew.getId())) &&
                        ! request.getParameter("comments" + sqrew.getId()).equals(sqrew.getComment())){

                    sqrew.setComment(request.getParameter("comments" + sqrew.getId()));
                    saveKey = true;
                }


                if (Strings.isNotBlank(request.getParameter("name" + sqrew.getId())) &&
                        ! request.getParameter("name" + sqrew.getId()).equals(sqrew.getName())) {

                    sqrew.setName(request.getParameter("name" + sqrew.getId()));
                    saveKey = true;
                }


                if (Strings.isNotBlank(request.getParameter("limit" + sqrew.getId()))){

                    if (sqrew.getLimit() == null) {
                        sqrew.setLimit(Integer.parseInt(request.getParameter("limit" + sqrew.getId())));
                        saveKey = true;
                    }
                    else if (Integer.parseInt(request.getParameter("limit" + sqrew.getId())) != sqrew.getLimit()) {
                        sqrew.setLimit(Integer.parseInt(request.getParameter("limit" + sqrew.getId())));
                        saveKey = true;
                    }

                }


                if (Strings.isNotBlank(request.getParameter("classSqrew" + sqrew.getId()))){

                    if (sqrew.getClassSqrew() == null) {
                        sqrew.setClassSqrew(Double.parseDouble(request.getParameter("classSqrew" + sqrew.getId())));
                        saveKey = true;
                    }
                    else if (Double.parseDouble(request.getParameter("classSqrew" + sqrew.getId())) != sqrew.getClassSqrew()) {
                        sqrew.setClassSqrew(Double.parseDouble(request.getParameter("classSqrew" + sqrew.getId())));
                        saveKey = true;
                    }
                }

                if (Strings.isNotBlank(request.getParameter("depth" + sqrew.getId()))) {

                    if (sqrew.getDepth() == null) {
                        sqrew.setDepth(Double.parseDouble(request.getParameter("depth" + sqrew.getId())));
                     saveKey = true;
                    } else if (Double.parseDouble(request.getParameter("depth" + sqrew.getId())) != sqrew.getDepth()) {
                        sqrew.setDepth(Double.parseDouble(request.getParameter("depth" + sqrew.getId())));
                        saveKey = true;
                    }
                }

                if(saveKey){
                    SqrewEntity sqrewBasa = sqrewDaoRepository.getSqrewById(sqrew.getId());

                    sqrewBasa.setClassSqrew(sqrew.getClassSqrew());
                    sqrewBasa.setName(sqrew.getName());
                    sqrewBasa.setComment(sqrew.getComment());
                    sqrewBasa.setLimit(sqrew.getLimit());
                    sqrewBasa.setDataCreate(LocalDateTime.now());
                    sqrewBasa.setDepth(sqrew.getDepth());
                    sqrewBasa.setUser(user);

                    sqrewDaoRepository.saveSqrew(sqrewBasa);
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
     * 111
     * */
    public  boolean saveNewSqrew(Integer UserId, HttpServletRequest request){
//        "bolt_name_new"
//    "bolt_limit_new"
//    "bolt_classBolt_new"
//    "bolt_dataCreate_new"
//    "bolt.comment_new"
        boolean saveKey = false;

        String name = null;
        Integer limit = 0;
        Double classSqrew = 0.0;
        String commets = null;
        Double depth = 0.8;

        if (Strings.isNotBlank(request.getParameter("sqrew_name_new"))){

            name = request.getParameter("sqrew_name_new");
            saveKey = true;

            if (Strings.isNotBlank(request.getParameter("sqrew_limit_new"))) {

                limit = Integer.parseInt(request.getParameter("sqrew_limit_new"));
            }

            if (Strings.isNotBlank(request.getParameter("sqrew_classSqrew_new"))) {

                classSqrew = Double.parseDouble(request.getParameter("sqrew_classSqrew_new"));
            }


            if (Strings.isNotBlank(request.getParameter("sqrew_depth_new"))) {

                depth = Double.parseDouble(request.getParameter("sqrew_depth_new"));
            }

            if (Strings.isNotBlank(request.getParameter("sqrew.comment_new"))){

                commets = request.getParameter("sqrew.comment_new");

            }
        }

        if (saveKey && Objects.nonNull(UserId)){
            UserEntity user = userDao.getUserById(UserId);

            sqrewDaoRepository.saveSqrew(
                    SqrewEntity.builder().
                            classSqrew(classSqrew).
                            comment(commets).
                            dataCreate(LocalDateTime.now()).
                            limit(limit).
                            name(name).
                            user(user).
                            depth(depth).
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
    public boolean deleteSqrew(int sqrewId){

        return sqrewDaoRepository.deleteSqrew(sqrewId);
    }

    /**
     * ���������� ������ ��������� ����������
     * */
    public double calcClassSqrew(int limitSqrew) {

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

//        limitGOSTlist.add(limitSqrew); //���������� � ���� �������� �����
        int count = 0;

        for (; count < limitGOSTsort.size(); count++){
            if( (limitSqrew - limitGOSTsort.get(0)) < 0){
                break;
            }
            if (limitSqrew > limitGOSTsort.get(limitGOSTsort.size() - 1)){
                count = limitGOSTsort.size() - 1;
                break;
            }
            if ((limitSqrew - limitGOSTsort.get(count)) <= (limitGOSTsort.get(count + 1) - limitSqrew)){
                break;
            }
        }

        return classGOST.get(limitGOSTsort.get((count)));
    }


}
