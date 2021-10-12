package com.example.kotlinDemo.kotlin.dataClass

fun nameToUppercase(name: Name): Name {
    return name.copy(firstName = name.firstName.uppercase(), lastName = name.lastName?.uppercase())
}