package com.ev.momcalcboot.enums.koeff;


//two three four five six seven eight nine ten
public enum StrengthInThread_2 {
    ONE_TURN (0.75),
    TWO_TURN (0.25);


    private final double koeff;


    StrengthInThread_2(double koeff) {
        this.koeff = koeff;
    }

    public double getKoeff() {
        return koeff;
    }
}
