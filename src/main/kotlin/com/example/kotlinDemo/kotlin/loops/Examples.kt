package com.example.kotlinDemo.kotlin.loops


class Examples {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            // for

            val fruits = listOf("banana", "avocado", "apple", "grape")

            for (fruit in fruits) {
                println("Fruit: $fruit")
            }

            val range = 1..10
            for (x in range) {
                println("$x * 2 = ${x * 2} ")
            }

            // while

            val startNumber = 20
            var counter = 0

            while (counter < 10) {
                counter++
                println("$startNumber - $counter = ${startNumber - counter}")
            }
        }
    }
}