package com.ev.momcalcboot.service.internal;

import java.util.regex.Pattern;

public class ParserNumber {

    public static double toDouble(String str){

        if (str.isBlank()) {
            return 0.0;
        }
        StringBuilder builder = new StringBuilder();

        char[] arrChar = str.toCharArray();

        int count = 0;  // счетчик чисел после запятой
        int countPoint = 0;

        for (char ch :arrChar){

            if (count > 2){
                break;
            }
            if (countPoint > 0){
                count++;
            }

            if (Character.isDigit(ch)){
                builder.append(ch);
                continue;
            }

            if(countPoint == 0 && (ch == ',' || ch == '.')){
                builder.append('.');
                countPoint++;
            }
        }

//        если строка начинается с "." добавить 0 спереди
        if(builder.charAt(0) == '.'){
            builder.insert(0, '0');
        }
        return Double.parseDouble(builder.toString());
    }

    public static int toInt(String str){
        if (str.isBlank()) {
            return 0;
        }
        boolean firstNumberNull = true;
        StringBuilder builder = new StringBuilder();

        char[] arrChar = str.toCharArray();



        for (char ch :arrChar){

            if (firstNumberNull && ch =='0'){
                continue;
            }
            if (Character.isDigit(ch)){
                builder.append(ch);
                firstNumberNull = false;
                continue;
            }

            if(ch == ',' || ch == '.'){
                break;
            }
        }
        //        если строка начинается с цифры коме 0 то вернуть и парсить

        if(Pattern.matches("[1-9]", Character.toString(builder.charAt(0)))){
            return Integer.parseInt(builder.toString());
        }
        else {
            return 0;
        }



    }

}
