package com.ev.momcalcboot.enums.koeff;


//two three four five six seven eight nine ten
public enum StrengthInThread_6 {
    ONE_TURN (0.423),
    TWO_TURN (0.22),


    TWO_THREE (0.136),

    TWO_FOUR (0.096),

    TWO_FIVE (0.07),

    TWO_SIX (0.055);

//    Первый виток: около 35–45% общей нагрузки.
//    Второй виток: около 18–23% оставшейся нагрузки.
//    Третий виток: около 13–18% оставшейся нагрузки.
//    Четвертый виток: около 10–14% оставшейся нагрузки.
//    Пятый виток: около 7–11% оставшейся нагрузки.
//    Шестой виток: около 4–8% оставшейся нагрузки.



    private final double koeff;


    StrengthInThread_6(double koeff) {
        this.koeff = koeff;
    }

    public double getKoeff() {
        return koeff;
    }
}
