package pl.allegroumk.allediet.repository.inMemory

import pl.allegroumk.allediet.repository.MealsRepository
import pl.allegroumk.allediet.service.model.Ingredient
import pl.allegroumk.allediet.service.model.Meal
import pl.allegroumk.allediet.service.model.MealToUpdate
import pl.allegroumk.allediet.service.model.MealsSummary

class InMemoryMealsRepository : MealsRepository {

    private val meals: MutableSet<InMemoryMeal> = HashSet()

    override fun getAllMeals() = meals.map { it.toDomain() }

    override fun getMeal(id: String) = meals.firstOrNull { it.id == id }?.toDomain()

    override fun insertMeal(meal: Meal): Meal {
        meals.add(meal.toDocument())
        return meal
    }

    override fun updateMeal(mealToUpdate: Meal) {
        deleteMeal(mealToUpdate.id)
        meals.add(mealToUpdate.toDocument())
    }

    override fun deleteMeal(id: String) {
        meals.removeIf { it.id == id }
    }

    override fun getMealsWithCaloriesBetween(minCalories: Int, maxCalories: Int) =
        meals.filter { it.calories in (minCalories + 1) until maxCalories }.map { it.toDomain() }

    override fun getAllMealsSortedByCaloriesAscending(): Iterable<Meal> {
        TODO("Not yet implemented")
    }

    override fun getMealsWithMoreIngredientsThan(count: Int): Iterable<Meal> {
        TODO("Not yet implemented")
    }

    override fun getMealsByNames(names: List<String>): Iterable<Meal> {
        TODO("Not yet implemented")
    }

    override fun deleteMealsByName(name: String) {
        TODO("Not yet implemented")
    }

    override fun findAndModify(mealToUpdate: MealToUpdate): Meal? {
        TODO("Not yet implemented")
    }

    override fun getMealsSummary(): MealsSummary {
        TODO("Not yet implemented")
    }

    private fun InMemoryMeal.toDomain(): Meal {
        return Meal(id, name, calories, ingredients.map { Ingredient(it.name, it.calories) }, createdAt, updatedAt)
    }

    private fun Meal.toDocument(): InMemoryMeal {
        return InMemoryMeal(
            id,
            name,
            calories,
            ingredients.map { InMemoryIngredient(it.name, it.calories) },
            createdAt,
            updatedAt
        )
    }
}