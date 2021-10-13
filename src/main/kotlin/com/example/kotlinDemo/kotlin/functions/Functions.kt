package com.example.kotlinDemo.kotlin.functions

fun hello() {
    println("Hello world")
}

fun sum1kt(a: Int, b: Int): Int {
    return a + b
}

//fun sum2kt(a: Int, b: Int) = a + b
//
//fun helloWithDefault(name: String = "UMK") {
//    println("Hello $name")
//}
//
//fun sumFourWithWeights(a: Int = 0, b: Int = 0, c: Int = 0, d: Int = 0): Int {
//    return a + 2 * b + 3 * c + 4 * d
//}
//
//fun Int.multiplyBy2() = this * 2
//
//infix fun Int.dodaj(b: Int) = this + b