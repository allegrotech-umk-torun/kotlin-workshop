package pl.allegroumk.allediet.repository

import pl.allegroumk.allediet.service.model.Meal

interface MealsRepository {

    fun getAllMeals(): Iterable<Meal>

    fun getMeal(id: String): Meal?

    fun insertMeal(meal: Meal): Meal

    fun deleteMeal(id: String)

    fun updateMeal(mealToUpdate: Meal)

    // ---

    fun getMealsWithCaloriesBetween(minCalories: Int, maxCalories: Int): Iterable<Meal>

    fun getAllMealsSortedByCaloriesAscending(): Iterable<Meal>

    fun getMealsWithMoreIngredientsThan(count: Int): Iterable<Meal>

    fun getMealsByNames(names: List<String>): Iterable<Meal>

}