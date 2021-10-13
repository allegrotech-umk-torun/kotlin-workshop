package com.example.kotlinDemo.kotlin.classes

data class PlaceOfLive(val continent: Continent?)

data class Continent(val continentName: String, val country: Country?)

data class Country(val countryName: String, val region: Region?)

data class Region(val regionName: String, val city: City?)

data class City(val cityName: String?)
