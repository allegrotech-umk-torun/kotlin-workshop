package pl.allegroumk.allediet.repository

import pl.allegroumk.allediet.service.model.Ingredient
import pl.allegroumk.allediet.service.model.Meal

interface MealsRepository {

    fun getAllMeals(): Iterable<Meal>

    fun getMeal(id: String): Meal?

    fun insertMeal(meal: Meal): Meal

    fun deleteMeal(id: String)

    fun addIngredientsToMeal(idToUpdate: String, ingredientsToAdd: List<Ingredient>): Meal?
}