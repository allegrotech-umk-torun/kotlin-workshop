package pl.allegroumk.allediet.repository

import org.springframework.stereotype.Component
import pl.allegroumk.allediet.api.model.InputMeal
import pl.allegroumk.allediet.service.model.Ingredient
import pl.allegroumk.allediet.service.model.Meal
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashSet

@Component
class MealsRepository {
    private val meals: MutableSet<Meal> = HashSet()

    fun getAllMeals() = meals
    fun insertMeal(inputMeal: InputMeal) {

        meals.add(
            Meal(
                id = UUID.randomUUID(),
                name = inputMeal.name,
                calories = inputMeal.calories,
                ingredients = inputMeal.ingredients.map { Ingredient(it.name, it.calories) },
                createdAt = LocalDateTime.now()
            )
        )
    }
}