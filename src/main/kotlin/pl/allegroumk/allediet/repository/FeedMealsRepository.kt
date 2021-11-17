package pl.allegroumk.allediet.repository

import org.apache.commons.lang3.RandomStringUtils
import pl.allegroumk.allediet.service.model.Ingredient
import pl.allegroumk.allediet.service.model.Meal
import java.time.LocalDateTime
import javax.annotation.PostConstruct

class FeedMealsRepository(
    private val repository: MealsRepository
) {

    @PostConstruct
    fun feedMealsRepository() {
        if (repository.getAllMeals().none()) {
            repository.insertMeal(
                Meal(
                    RandomStringUtils.random(8, true, true),
                    "breakfast",
                    listOf(Ingredient("platki", 250), Ingredient("kawa", 50)),
                    LocalDateTime.now()
                )
            )
            repository.insertMeal(
                Meal(
                    RandomStringUtils.random(8, true, true),
                    "lunch",
                    listOf(Ingredient("omlet", 500)),
                    LocalDateTime.now()
                )
            )
            repository.insertMeal(
                Meal(
                    RandomStringUtils.random(8, true, true),
                    "dinner",
                    listOf(Ingredient("schabowy", 400), Ingredient("ziemniaki", 300)),
                    LocalDateTime.now()
                )
            )
            repository.insertMeal(
                Meal(
                    RandomStringUtils.random(8, true, true),
                    "supper",
                    listOf(Ingredient("kanapka", 300)),
                    LocalDateTime.now()
                )
            )
        }
    }
}