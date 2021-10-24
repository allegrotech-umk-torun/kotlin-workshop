package com.example.kotlinDemo

import com.example.kotlinDemo.kotlin.functions.connect
import com.example.kotlinDemo.kotlin.functions.isBigger
import com.example.kotlinDemo.kotlin.functions.kwadratSumy
import com.example.kotlinDemo.kotlin.functions.sumaKwadratow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//@SpringBootTest
class FunctionsExercises {

	@Test
	fun kwadratSumyTest() {

		//when:
		val wynik = kwadratSumy(3, 4)

		//then:
		assertEquals(wynik, 49)
	}

	@Test
	fun sumaKwadratowTest() {

		//when:
		val wynik = sumaKwadratow(3, 4)

		//then:
		assertEquals(wynik, 25)
	}

	@Test
	fun isBiggerTest() {
		assertEquals(5.isBigger(7), false)
		assertEquals(5.isBigger(5), false)
		assertEquals(5.isBigger(4), true)

	}

	@Test
	fun connectTest() {
		assertEquals("rower" connect "czerwony", "rowerCZERWONY")
		assertEquals("ROWER" connect "czerwony", "rowerCZERWONY")
		assertEquals("RoWeR" connect "CzErWoNy", "rowerCZERWONY")
		assertEquals("RoWeR" connect "", "rower")
		assertEquals("" connect "CzErWoNy", "CZERWONY")

	}
}
