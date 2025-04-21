package com.ev.momcalcboot.enums.koeff;


//two three four five six seven eight nine ten
public enum StrengthInThread_3 {
    ONE_TURN (0.55),
    TWO_TURN (0.3),

    TWO_THREE (0.15);

    private final double koeff;


    StrengthInThread_3(double koeff) {
        this.koeff = koeff;
    }

    public double getKoeff() {
        return koeff;
    }
}
