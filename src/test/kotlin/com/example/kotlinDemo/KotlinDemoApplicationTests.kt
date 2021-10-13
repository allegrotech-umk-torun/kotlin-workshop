package com.example.kotlinDemo

import com.example.kotlinDemo.kotlin.functions.sumFourWithWeights
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KotlinDemoApplicationTests {

	@Test
	fun functionsTest() {

		//when:
		val wynik = sumFourWithWeights(1, 2, 3, 4)

		//then:
		assertEquals(wynik, 30)

	}

}
