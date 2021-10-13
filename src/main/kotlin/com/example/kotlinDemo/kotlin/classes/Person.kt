package com.example.kotlinDemo.kotlin.classes

class Person(
    val name: String,
    var nickName: String?,
    var isMarried: Boolean
) {
    val upperCaseName = name.uppercase()

    fun getDescription(): String {
        return "$name is married: $isMarried"
    }

    override fun toString(): String {
        return "Person(name='$name', nickName=$nickName, isMarried=$isMarried, upperCaseName='$upperCaseName')"
    }
}

//open class PersonWithDefault(
//    val name: String,
//    var nickName: String? = null,
//    var isMarried: Boolean = false
//) {
//
//    val upperCaseName = name.uppercase()
//
//    fun getNicknameIfExists(): String {
//        return nickName ?: "nick name is not set"
//    }
//
//    override fun toString(): String {
//        return "Person(name='$name', nickName=$nickName, isMarried=$isMarried, upperCaseName='$upperCaseName')"
//    }
//}

//class PersonWithDetails(name: String, nickName: String? = null, isMarried: Boolean = false, val placeOfBirth: String) : PersonWithDefault(name, nickName, isMarried) {
//
//    override fun toString(): String {
//        return "PersonWithDetails(name='$name', nickName=$nickName, isMarried=$isMarried, upperCaseName='$upperCaseName, placeOfBirth='$placeOfBirth')"
//    }
//}