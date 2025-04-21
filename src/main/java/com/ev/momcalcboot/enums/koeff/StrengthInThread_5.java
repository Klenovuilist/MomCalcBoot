package com.ev.momcalcboot.enums.koeff;


//two three four five six seven eight nine ten
public enum StrengthInThread_5 {
    ONE_TURN (0.45),
    TWO_TURN (0.23),

    TWO_THREE (0.143),

    TWO_FOUR (0.102),

    TWO_FIVE (0.075);

//    Первый виток: около 40–50% общей нагрузки.
//    Второй виток: около 20–25% оставшейся нагрузки.
//    Третий виток: около 15–20% оставшейся нагрузки.
//    Четвертый виток: около 10–15% оставшейся нагрузки.
//    Пятый виток: около 5–10% оставшейся нагрузки.
    private final double koeff;


    StrengthInThread_5(double koeff) {
        this.koeff = koeff;
    }

    public double getKoeff() {
        return koeff;
    }
}
