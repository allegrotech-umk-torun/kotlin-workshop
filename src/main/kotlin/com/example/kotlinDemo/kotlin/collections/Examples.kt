package com.example.kotlinDemo.kotlin.collections

class Examples {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            // listy

            val fruits = listOf("banana", "avocado", "apple", "grape")
            println("fruits = $fruits")

            val fruitsWithDuplicates = listOf("banana", "avocado", "apple", "grape", "banana")
            println("fruitsWithDuplicates = $fruitsWithDuplicates")

            //zbiory

            val setOfFruits = setOf("banana", "avocado", "apple", "grape", "banana")
            println("setOfFrits = $setOfFruits")

            //mapy

            val fruitsMap = mapOf("banana" to "yellow", "avocado" to "green", "apple" to "red")
            println("fruitsMap = $fruitsMap")


            val afterOperations = fruits
                .filter { it.startsWith("a") }
                .sortedBy { it.length }
                .map { it.uppercase() }
                .map { Pair(it, it.length) }
                .toMap()

                afterOperations.forEach { print("$it ") }

        }
    }
}