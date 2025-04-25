package com.ev.momcalcboot.enums.koeff;


//two three four five six seven eight nine ten
public enum StrengthInThread_9 {
    ONE_TURN (0.31),
    TWO_TURN (0.21),

    TWO_THREE (0.148),

    TWO_FOUR (0.11),

    TWO_FIVE (0.082),

    TWO_SIX (0.06),

    TWO_SEVEN (0.04),

    TWO_EIGHT (0.025),

    TWO_NINE (0.015);




   private final double koeff;


    StrengthInThread_9(double koeff) {
        this.koeff = koeff;
    }

    public double getKoeff() {
        return koeff;
    }
}
