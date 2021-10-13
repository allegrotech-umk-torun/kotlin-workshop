package com.example.kotlinDemo.kotlin.expresions

fun maxKt(a: Int, b: Int): Int {
    return if (a > b) a else b
}

fun rangeKt(number: Int): String {
    return when {
        number < 0 -> "([)..., 0)"
        number < 10 -> "<0, 10)"
        else -> "<10, ...)"
    }
}

fun numberName(number: Int): String {
    return when(number) {
        0 -> "zero"
        1 -> "jeden"
        2 -> "dwa"
        else -> "jakaś liczba"
    }
}

fun booleanName(boolean: Boolean): String {
    return when(boolean) {
        true -> "prawda"
        false -> "fałsz"
    }
}