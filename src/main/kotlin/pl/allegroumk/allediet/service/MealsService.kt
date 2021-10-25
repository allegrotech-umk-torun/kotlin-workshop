package pl.allegroumk.allediet.service

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Component
import pl.allegroumk.allediet.api.model.InputMeal
import pl.allegroumk.allediet.api.model.UpdateMeal
import pl.allegroumk.allediet.repository.inMemoryRepository.ImMemoryMealsRepository
import pl.allegroumk.allediet.service.model.Ingredient
import pl.allegroumk.allediet.service.model.Meal
import java.time.LocalDateTime

@Component
class MealsService(
    val mealsRepository: ImMemoryMealsRepository
) {

    fun getAllMeals() = mealsRepository.getAllMeals()

    fun getMeal(id: String) = mealsRepository.getMeal(id)

    fun insertMeal(inputMeal: InputMeal): Meal {
        val mealToAdd = Meal(
            id = RandomStringUtils.random(8, true, true),
            name = inputMeal.name,
            ingredients = inputMeal.ingredients.map { Ingredient(it.name, it.calories) },
            createdAt = LocalDateTime.now()
        )
        mealsRepository.insertMeal(mealToAdd)
        return mealToAdd
    }

    fun updateMeal(updateMeal: UpdateMeal): Meal? {
        return mealsRepository.addIngredientsToMeal(updateMeal.id, updateMeal.ingredients.map { Ingredient(it.name, it.calories) })
    }

    fun deleteMeal(id: String) = mealsRepository.deleteMeal(id)
}