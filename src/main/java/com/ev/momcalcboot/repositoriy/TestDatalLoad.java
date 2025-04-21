package com.ev.momcalcboot.repositoriy;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class TestDatalLoad {


    private final JdbcTemplate jdbcTemplate;



    public Map<String, Boolean> loadBdTestData(boolean isLoad) {

        Map<String, Boolean> mapMessage = new HashMap<>();

        if(isLoad){
            try {
                jdbcTemplate.update("insert into users ( user_name, password_user, role_user, data_user) " +
                        "values( 'Admin',  'admin', 'admin', '2025-03-03')");
                mapMessage.put("Admin_user", true);

            } catch (RuntimeException e) {
                mapMessage.put("Admin_user", false);

            }

            try {
                jdbcTemplate.update("insert into bolt_entity (id, bolt_name, bolt_limit, id_user, comment, data_create, classbolt) " +
                        "VALUES (100,  'Ст20Х',900, 1,' ','2024-10-23', 10.6)," +
                        " (101,'30ХГСА',1050, 1,' ','2024-10-23',11.8)");
                mapMessage.put("bolt_entity", true);

            } catch (RuntimeException ee) {
                mapMessage.put("bolt_entity", false);

            }

            try {
                jdbcTemplate.update(" insert into sqrew_entity (id, sqrew_name, sqrew_limit, sqrew_depth, id_user, comment, data_create, classsqrew)" +
                        "VALUES(100,'Ст3', 350, 0.8 ,1,' ','2024-10-02', 5 )," +
                        "(101,'Ст20', 470, 0.9, 1,' ','2024-10-24', 5)");
                mapMessage.put("sqrew_entity", false);

            } catch (RuntimeException e) {
                mapMessage.put("sqrew_entity", false);

            }

            try {
                jdbcTemplate.update(" insert into materals_db (limit_strength, materials, user_id, strength_class, comments, k_depth, data_create, coefffricthread, coefffricbolthead, safetyfactor, materialscrew, limit_screw, class_screw)" +
                        " values (320,'Сталь 10 (углеродистая)' ,1, 4.8, 'шаг крупный',0.8, '2024-10-02 16:09:33.213472' ,0.16,0.16,0.75,'Сталь 20' ,300, 4)");
                mapMessage.put("materals_db", true);

            } catch (RuntimeException e) {
                mapMessage.put("materals_db", false);
            }

            try {
                jdbcTemplate.update("insert into thread_db (thread, step_thread, d_midlethread, d_bolt, d_hole, d_head) values " +
                        "('M3',0.5,2.675,3,3.1,5)," +
                        "('M10',1.5,9.026,10,10.3,16)," +
                        "('M2.5',0.45,2.273,2.5,2.6,4.35)," +
                        "('M6',1,5.35,6,6.2,10)," +
                        "('M7',1,6.35,7,7.2,11)," +
                        "('M5',0.8,4.48,5,5.2,8)," +
                        "('M4',0.6,3.545,4,4.2,7)," +
                         "('M8',1.25,7.188,8,8.2,13);" +
                        "INSERT INTO public.thread_db (thread, d_bolt, d_hole, d_head, step_thread, d_midlethread) values" +
                        "('M1.6', 1.6, 1.65, 3, '0.35', 1.373)," +
                        " ('M2', 2, 2.1, 3.8, '0.4', 1.74)," +
                        "('M12', 12, 12.5, 18, '1.75', 10.863)," +
                        "('M14', 14, 14.6, 21, '2', 12.701)," +
                        "('M16', 16, 16.8, 24, '2', 14.701)," +
                        "('M18', 18, 18.8, 27, '2.5', 16.376)," +
                        "('M20', 20, 21, 30, '2.5', 18.376) ");


                mapMessage.put("thread_db", true);

            } catch (RuntimeException e) {
                mapMessage.put("thread_db", false);

            }
        }
        else {
            mapMessage.put("Admin_user", false);
            mapMessage.put("thread_db", false);
            mapMessage.put("materals_db", false);
            mapMessage.put("sqrew_entity", false);
            mapMessage.put("bolt_entity", false);

        }




        return mapMessage;
    }

    public boolean loadData(String sql){

        try {
            jdbcTemplate.update(sql);
            return true;

        } catch (RuntimeException e) {
            return false;
        }
    }












}
