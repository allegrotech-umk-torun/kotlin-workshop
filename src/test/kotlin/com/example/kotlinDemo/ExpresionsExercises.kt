package com.example.kotlinDemo

import com.example.kotlinDemo.kotlin.expresions.isEven
import com.example.kotlinDemo.kotlin.expresions.monthName
import com.example.kotlinDemo.kotlin.functions.connect
import com.example.kotlinDemo.kotlin.functions.isBigger
import com.example.kotlinDemo.kotlin.functions.kwadratSumy
import com.example.kotlinDemo.kotlin.functions.sumaKwadratow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//@SpringBootTest
class ExpresionsExercises {

	@Test
	fun isEvenTest() {

		//then:
		assertEquals(1.isEven(), false)
		assertEquals(7.isEven(), false)
		assertEquals((-3).isEven(), false)
		assertEquals(0.isEven(), true)
		assertEquals(12.isEven(), true)
	}

	@Test
	fun monthNameTest() {

		assertEquals(monthName(3), "Marzec")
		assertEquals(monthName(9), "Wrzesień")
		assertEquals(monthName(0), null)
		assertEquals(monthName(22), null)
		assertEquals(monthName(-7), null)
	}
}
