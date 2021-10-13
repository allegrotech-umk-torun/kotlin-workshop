package com.example.kotlinDemo.kotlin.classes

class Example {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

// class

            val person = Person("Ryszard", "Rychu", false)
            println("person = $person")

//            println("upperCase name = ${person.upperCaseName}")
//
//            person.isMarried = true
//            println("person after married = $person")
//
//            println("status = ${person.getDescription()}")

//            val person2 = Person("Rysiek")

//            val personWithDefault = PersonWithDefault("Krystyna")
//            println("personWithDefault = $personWithDefault")
//
//            println("personWithDefault nickname = ${personWithDefault.getNicknameIfExists()}")
//
//            personWithDefault.nickName = "Krycha"
//
//            println("personWithDefault nickname = ${personWithDefault.getNicknameIfExists()}")
//
//            val personWithDetails = PersonWithDetails("Henryk", placeOfBirth = "Toruń")
//
//            println("personWithDetails = $personWithDetails")




//data class

//            val user1 = User(1, Name("Jan", "Kowalski"), 23)
//            println("User1 $user1")
//
//            println("User1 id = ${user1.userId}")
//
//            println("User1 is adult = ${user1.isAdult()}")
//
//            val userPromoted = user1.copy(status = UserStatus.PREMIUM)
//
//            println("userPromoted = $userPromoted")
//
//            val user3 = User(userId= 3, Name(), age = 29)
//
//            println("User3 = $user3")
//
//            val placeOfLive1 = PlaceOfLive(Continent("Europa", Country("Poland", Region("kujawsko-pomorskie", City("Toruń")))))
//
//            val cityName1 = placeOfLive1.continent?.country?.region?.city
//
//            println("cityName1 = $cityName1")
//
//            val placeOfLive2 = PlaceOfLive(Continent("Europa", null))
//
//            val cityName2a = placeOfLive2.continent?.country?.region?.city
//            println("cityName2a = $cityName2a")
//
//            val cityName2b = placeOfLive2.continent?.country?.region?.city ?: "data is not complete"
//            println("cityName2b = $cityName2b")
        }
    }
}