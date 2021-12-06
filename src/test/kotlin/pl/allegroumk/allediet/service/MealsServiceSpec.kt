package pl.allegroumk.allediet.service

import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import pl.allegroumk.allediet.repository.MealsRepository
import pl.allegroumk.allediet.repository.inMemory.InMemoryMealsRepository
import pl.allegroumk.allediet.service.model.Ingredient
import pl.allegroumk.allediet.service.model.Meal
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MealsServiceSpec {

    private val firstMealToAdd = prepareMeal("breakfast", listOf(Ingredient("kawa", 50)))
    private val secondMealToAdd = prepareMeal("lunch", listOf(Ingredient("płatki", 100)))

    private lateinit var mealsRepository: MealsRepository
    private lateinit var mealsService: MealsService

    @BeforeEach
    fun beforeEach() {
        mealsRepository = InMemoryMealsRepository()
        mealsService = MealsService(mealsRepository)
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    fun `should return meals with calories between`(minCalories: Int, maxCalories: Int, expectedMeals: List<Meal>) {
        //given
        mealsRepository.insertMeal(firstMealToAdd)
        mealsRepository.insertMeal(secondMealToAdd)

        //when
        val foundMeals = mealsService.getMealsWithCaloriesBetween(minCalories, maxCalories)

        //then
        assertThat(foundMeals).containsAll(expectedMeals)
    }

    private fun getTestData() = listOf(
        Arguments.of(40, 60, listOf(firstMealToAdd))
        // TODO
    )

    private fun prepareMeal(name: String, ingredients: List<Ingredient>) =
        Meal(
            RandomStringUtils.random(8, true, true),
            name,
            ingredients,
            LocalDateTime.now()
        )

}
