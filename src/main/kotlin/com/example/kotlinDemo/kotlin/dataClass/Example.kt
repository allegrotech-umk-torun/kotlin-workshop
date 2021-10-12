package com.example.kotlinDemo.kotlin.dataClass

class Example {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val user1 = User(1, Name("Jan", "Kowalski"), 23)
            println("User1 $user1")

            println("User1 id = ${user1.userId}")

            val user2 = user1.copy(userId = 2, age = 24)

            println("User2 = $user2")

            val user3 = User(userId= 3, Name(), age = 29)

            println("User3 = $user3")

            val user4 = user1.copy(name = nameToUppercase(user1.name))

            println("User4 = $user4")

        }
    }
}