package com.ev.momcalcboot.enums.koeff;


//two three four five six seven eight nine ten
public enum StrengthInThread_10 {
    ONE_TURN (0.306),
    TWO_TURN (0.205),

    TWO_THREE (0.146),

    TWO_FOUR (0.108),

    TWO_FIVE (0.081),

    TWO_SIX (0.058),

    TWO_SEVEN (0.041),

    TWO_EIGHT (0.027),

    TWO_NINE (0.016),

    TWO_TEN (0.012);




   private final double koeff;


    StrengthInThread_10(double koeff) {
        this.koeff = koeff;
    }

    public double getKoeff() {
        return koeff;
    }
}
