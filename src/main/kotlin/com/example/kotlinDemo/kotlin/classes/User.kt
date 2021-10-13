package com.example.kotlinDemo.kotlin.classes

import com.example.kotlinDemo.kotlin.classes.UserStatus.*

data class User(
    val userId: Int,
    val name: Name = Name(),
    val age: Int,
    val status: UserStatus = REGULAR
) {

    fun isAdult() = age >= 18

}

data class Name(
    val firstName: String = "Firstname",
    val lastName: String? = null,
)

enum class UserStatus {
    REGULAR,
    PREMIUM
}