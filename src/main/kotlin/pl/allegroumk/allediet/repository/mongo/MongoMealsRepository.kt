package pl.allegroumk.allediet.repository.mongo

import pl.allegroumk.allediet.repository.MealsRepository
import pl.allegroumk.allediet.service.model.Ingredient
import pl.allegroumk.allediet.service.model.Meal

class MongoMealsRepository(
    private val repository: SpringMongoMealsRepository
) : MealsRepository {

    override fun getAllMeals(): Iterable<Meal> {
        return repository.findAll().map { it.toDomain() }
    }

    override fun insertMeal(meal: Meal): Meal {
        return repository.save(meal.toDocument()).toDomain()
    }

    override fun getMeal(id: String): Meal? {
        TODO("Not yet implemented")
    }

    override fun deleteMeal(id: String) {
        TODO("Not yet implemented")
    }

    override fun updateMeal(mealToUpdate: Meal) {
        TODO("Not yet implemented")
    }

    override fun getMealsWithCaloriesBetween(minCalories: Int, maxCalories: Int) =
        repository.findByCaloriesBetween(minCalories, maxCalories).map { it.toDomain() }

    override fun getAllMealsSortedByCaloriesAscending() = repository.findAllByOrderByCaloriesAsc().map { it.toDomain() }

    override fun getMealsWithMoreIngredientsThan(count: Int) =
        repository.findMealsWithMoreIngredientsThan(count).map { it.toDomain() }

    override fun getMealsByNames(names: List<String>): Iterable<Meal> {
        TODO("Not yet implemented")
    }

    private fun MongoMeal.toDomain() =
        Meal(
            id,
            name,
            calories,
            ingredients.map { Ingredient(it.name, it.calories) },
            createdAt,
            updatedAt
        )

    private fun Meal.toDocument() =
        MongoMeal(
            id,
            name,
            calories,
            ingredients.map { MongoIngredient(it.name, it.calories) },
            createdAt,
            updatedAt
        )
}