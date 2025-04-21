package com.ev.momcalcboot.enums.koeff;


//two three four five six seven eight nine ten
public enum StrengthInThread_7 {
    ONE_TURN (0.395),
    TWO_TURN (0.21),

    TWO_THREE (0.137),

    TWO_FOUR (0.095),

    TWO_FIVE (0.065),

    TWO_SIX (0.053),

    TWO_SEVEN (0.045);

//    Первый виток: около 33–43% общей нагрузки.
//    Второй виток: около 17–22% оставшейся нагрузки.
//    Третий виток: около 12–17% оставшейся нагрузки.
//    Четвертый виток: около 9–13% оставшейся нагрузки.
//    Пятый виток: около 7–11% оставшейся нагрузки.
//    Шестой виток: около 5–9% оставшейся нагрузки.
//    Седьмой виток: около 3–7% оставшейся нагрузки.



    private final double koeff;


    StrengthInThread_7(double koeff) {
        this.koeff = koeff;
    }

    public double getKoeff() {
        return koeff;
    }
}
