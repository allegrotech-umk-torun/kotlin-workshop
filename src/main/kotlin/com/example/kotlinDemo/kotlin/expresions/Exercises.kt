package com.example.kotlinDemo.kotlin.expresions



// uzupełnij funkcję isEven rozszerzającą klasę Int mówiącą czy liczba jest parzysta

fun Int.isEven() = false


// stwórz funkcję przyjmującą jako parametr liczbę całkowitą i zwracającą odpoiwadający mu miesiąć (w jężyku polskim) w kalendarzu. W przypadku liczby z poza zakresu zwracaj null. Uzupełnij i użyj klasę Month

fun monthName(monthNumber: Int) {

}

enum class Month(polishName: String) {
    JANUARY("Styczeń"),
    FEBRUARY("Luty")
}