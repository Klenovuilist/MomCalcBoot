package com.ev.momcalcboot.enums.koeff;


//two three four five six seven eight nine ten
public enum StrengthInThread_4 {
    ONE_TURN (0.48),
    TWO_TURN (0.24),

    TWO_THREE (0.16),

    TWO_FOUR (0.12);

//    Первый виток: около 45–55% общей нагрузки.
//    Второй виток: около 20–25% оставшейся нагрузки.
//    Третий виток: около 15–20% оставшейся нагрузки.
//    Четвертый виток: около 10–15% оставшейся нагрузки.

    private final double koeff;


    StrengthInThread_4(double koeff) {
        this.koeff = koeff;
    }

    public double getKoeff() {
        return koeff;
    }
}
