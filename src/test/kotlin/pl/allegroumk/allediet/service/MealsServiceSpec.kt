package pl.allegroumk.allediet.service

import com.github.benmanes.caffeine.cache.Caffeine
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.RestTemplate
import pl.allegroumk.allediet.outgoing.spoonacular.SpoonacularClient
import pl.allegroumk.allediet.outgoing.spoonacular.SpoonacularClientConfiguration
import pl.allegroumk.allediet.outgoing.spoonacular.SpoonacularService
import pl.allegroumk.allediet.outgoing.spoonacular.model.SpoonacularIngredientInformation
import pl.allegroumk.allediet.outgoing.spoonacular.model.SpoonacularNutrition
import pl.allegroumk.allediet.repository.MealsRepository
import pl.allegroumk.allediet.repository.inMemory.InMemoryMealsRepository
import pl.allegroumk.allediet.service.model.Ingredient
import pl.allegroumk.allediet.service.model.Meal
import wiremock.com.google.common.cache.LoadingCache
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MealsServiceSpec {

    private val firstMealToAdd = prepareMeal("breakfast", listOf(Ingredient("kawa", 50)))
    private val secondMealToAdd = prepareMeal("lunch", listOf(Ingredient("płatki", 100)))

    private lateinit var mealsRepository: MealsRepository
    private lateinit var mealsService: MealsService

    @BeforeEach
    fun beforeEach() {
        val testRestTemplate = RestTemplateBuilder()
            .setConnectTimeout(Duration.ofMillis(500))
            .setReadTimeout(Duration.ofMillis(1000))
            .build()
        val cache = Caffeine.newBuilder()
            .maximumSize(10)
            .refreshAfterWrite(600L, TimeUnit.SECONDS)
            .expireAfterWrite(3600, TimeUnit.SECONDS)
            .build<Long, SpoonacularIngredientInformation> { SpoonacularIngredientInformation(it, "name", 20, "unit", emptyList(), SpoonacularNutrition(
                emptyList())) }
        val spoonacularService = SpoonacularService(SpoonacularClient(testRestTemplate, testRestTemplate, SpoonacularClientConfiguration(null, null, null, null, null, null, null, null)), cache )
        mealsRepository = InMemoryMealsRepository()
        mealsService = MealsService(mealsRepository, spoonacularService)
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
        Arguments.of(40, 50, emptyList<Meal>()),
        Arguments.of(40, 60, listOf(firstMealToAdd)),
        Arguments.of(60, 80, emptyList<Meal>()),
        Arguments.of(80, 100, emptyList<Meal>()),
        Arguments.of(80, 120, listOf(secondMealToAdd)),
        Arguments.of(100, 120, emptyList<Meal>()),
        Arguments.of(40, 120, listOf(firstMealToAdd, secondMealToAdd)),
    )

    private fun prepareMeal(name: String, ingredients: List<Ingredient>) =
        Meal(
            RandomStringUtils.random(8, true, true),
            name,
            ingredients,
            LocalDateTime.now()
        )

}
