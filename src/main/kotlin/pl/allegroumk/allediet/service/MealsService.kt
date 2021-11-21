package pl.allegroumk.allediet.service

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Component
import pl.allegroumk.allediet.api.model.InputMeal
import pl.allegroumk.allediet.api.model.UpdateMeal
import pl.allegroumk.allediet.repository.MealsRepository
import pl.allegroumk.allediet.service.model.Ingredient
import pl.allegroumk.allediet.service.model.Meal
import pl.allegroumk.allediet.service.model.MealToUpdate
import java.time.LocalDateTime

@Component
class MealsService(
    val repository: MealsRepository
) {

    fun getAllMeals() = repository.getAllMeals()

    fun getMeal(id: String) = repository.getMeal(id)

    fun insertMeal(inputMeal: InputMeal): Meal {
        val mealToAdd = Meal(
            id = RandomStringUtils.random(8, true, true),
            name = inputMeal.name,
            ingredients = inputMeal.ingredients.map { Ingredient(it.name, it.calories) },
            createdAt = LocalDateTime.now()
        )
        repository.insertMeal(mealToAdd)
        return mealToAdd
    }

    fun updateMeal(updateMeal: UpdateMeal): Meal? {
        val mealToUpdate = getMeal(updateMeal.id)
        val updatedMeal = mealToUpdate?.let {
            it.copy(
                calories = it.calories + updateMeal.ingredients.sumOf { it.calories },
                ingredients = it.ingredients + updateMeal.ingredients.map { Ingredient(it.name, it.calories) },
                updatedAt = LocalDateTime.now()
            )
        }

        if (updatedMeal != null) {
            deleteMeal(updateMeal.id)
            repository.updateMeal(updatedMeal)
        }
        return updatedMeal
    }

    fun deleteMeal(id: String) = repository.deleteMeal(id)

    fun getMealsWithCaloriesBetween(minCalories: Int, maxCalories: Int) =
        repository.getMealsWithCaloriesBetween(minCalories, maxCalories)

    fun getAllMealsSortedInDB() = repository.getAllMealsSortedByCaloriesAscending()

    fun getMealsAdvanced() = repository.getMealsWithMoreIngredientsThan(3)

    fun getMeals(names: List<String>) = repository.getMealsByNames(names)

    fun deleteMealsByName(name: String) = repository.deleteMealsByName(name)

    fun findAndModify(updateMeal: UpdateMeal): Meal? {
        val mealToUpdate = MealToUpdate(
            updateMeal.id,
            updateMeal.ingredients.map { Ingredient(it.name, it.calories) }
        )
        return repository.findAndModify(mealToUpdate)
    }

    fun getMealsSummary() = repository.getMealsSummary()
}