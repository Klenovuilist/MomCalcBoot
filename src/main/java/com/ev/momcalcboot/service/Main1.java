package com.ev.momcalcboot.service;

import com.ev.momcalcboot.Entity.MaterialsEntity;
import com.ev.momcalcboot.repositoriy.BoltRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;


public class Main1 {

 static int a =1;
static String string = "eqeqw";
  public static class MainInner{

     String das = string;
     int a1 = a;
     int a2 = 3;

     MainInner(){
         new MainInner();
     }

     MainInner(int a){
         this();
     }
 }


    public static void main(String[] args) {

            a = 5;
        System.out.println(a);





    }




}
