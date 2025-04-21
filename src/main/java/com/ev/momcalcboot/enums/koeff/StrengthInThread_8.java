package com.ev.momcalcboot.enums.koeff;


//two three four five six seven eight nine ten
public enum StrengthInThread_8 {
    ONE_TURN (0.34),
    TWO_TURN (0.227),

    TWO_THREE (0.151),

    TWO_FOUR (0.11),

    TWO_FIVE (0.068),

    TWO_SIX (0.045),

    TWO_SEVEN (0.03),

    TWO_EIGHT (0.02);


//    Первый виток: около 30–40% общей нагрузки.
//    Второй виток: около 15–20% оставшейся нагрузки.
//    Третий виток: около 10–15% оставшейся нагрузки.
//    Четвертый виток: около 8–12% оставшейся нагрузки.
//    Пятый виток: около 6–10% оставшейся нагрузки.
//    Шестой виток: около 4–8% оставшейся нагрузки.
//    Седьмой виток: около 3–6% оставшейся нагрузки.
//    Восьмой виток: около 2–5% оставшейся нагрузки.





    private final double koeff;


    StrengthInThread_8(double koeff) {
        this.koeff = koeff;
    }

    public double getKoeff() {
        return koeff;
    }
}
