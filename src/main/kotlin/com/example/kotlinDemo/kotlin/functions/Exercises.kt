package com.example.kotlinDemo.kotlin.functions


// uzupełnij funkcję tak aby zwracała kwadrat sumy

fun kwadratSumy(a: Int, b: Int): Int {
    return (a + b) * (a + b)
}


// uzupełnij funkcję tak aby zwracałą sumę kwadratów
fun sumaKwadratow(a: Int, b: Int): Int {
    return a * a + b * b
}


// uzupełnij funkcję isBigger rozszerzającą klasę Int mówiącą czy liczba jest większa niż podana

fun Int.isBigger(a: Int) = this > a


// uzupełnij funkcję infix o nazwie "connect" łączącą dwa Stringi w taki sposób, że pierwsza część jest składa sięz małych liter,
// a druga z wielkicb tzn. wywołanie: "Czerwony" connext "Rower" powinno zwrócić Stringa "czerwonyROWER"

infix fun String.connect(argument: String) = this.lowercase() + argument.uppercase()

