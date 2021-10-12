package com.example.kotlinDemo.kotlin.dataClass

data class User(
    val userId: Int,
    val name: Name = Name(),
    val age: Int,

)

data class Name(
    val firstName: String = "Firstname",
    val lastName: String? = null,
)