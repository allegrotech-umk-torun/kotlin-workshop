package com.example.kotlinDemo.kotlin.expresions

import com.example.kotlinDemo.kotlin.expresions.Month.*


// uzupełnij funkcję isEven rozszerzającą klasę Int mówiącą czy liczba jest parzysta

fun Int.isEven() = this.mod(2) == 0


// stwórz funkcję przyjmującą jako parametr liczbę całkowitą i zwracającą odpoiwadający mu miesiąć (w jężyku polskim) w kalendarzu. W przypadku liczby z poza zakresu zwracaj null. Uzupełnij i użyj klasę Month

fun monthName(monthNumber: Int): String? {
    return when (monthNumber) {
        1 -> JANUARY.polishName
        2 -> FEBRUARY.polishName
        3 -> MARCH.polishName
        4 -> APRIL.polishName
        5 -> MAY.polishName
        6 -> JUNE.polishName
        7 -> JULY.polishName
        8 -> AUGUST.polishName
        9 -> SEPTEMBER.polishName
        10 -> OCTOBER.polishName
        11 -> NOVEMBER.polishName
        12 -> DECEMBER.polishName
        else -> null
    }

}

enum class Month(val polishName: String) {
    JANUARY("Styczeń"),
    FEBRUARY("Luty"),
    MARCH("Marzec"),
    APRIL("Kwiecień"),
    MAY("Maj"),
    JUNE("Czerwiec"),
    JULY("Lipiec"),
    AUGUST("Sierpień"),
    SEPTEMBER("Wrzesień"),
    OCTOBER("Październik"),
    NOVEMBER("Listopad"),
    DECEMBER("Grudzień")

}