package pl.allegroumk.allediet.repository.inMemoryRepository

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Component
import pl.allegroumk.allediet.service.model.Ingredient
import pl.allegroumk.allediet.service.model.Meal
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@Component
class FeedRepository(
    val mealsRepository: ImMemoryMealsRepository
) {

    @PostConstruct
    fun feedRepository() {
        mealsRepository.insertMeal(Meal(RandomStringUtils.random(8, true, true), "breakfast", listOf(Ingredient("platki", 250), Ingredient("kawa", 50)), LocalDateTime.now()))
        mealsRepository.insertMeal(Meal(RandomStringUtils.random(8, true, true), "lunch", listOf(Ingredient("omlet", 500)), LocalDateTime.now()))
        mealsRepository.insertMeal(Meal(RandomStringUtils.random(8, true, true), "dinner", listOf(Ingredient("schabowy", 400), Ingredient("ziemniaki", 300)), LocalDateTime.now()))
        mealsRepository.insertMeal(Meal(RandomStringUtils.random(8, true, true), "supper", listOf(Ingredient("kanapka", 300)), LocalDateTime.now()))
    }
}