package pl.allegroumk.allediet.repository.mongo

import org.springframework.data.repository.findByIdOrNull
import pl.allegroumk.allediet.repository.MealsRepository
import pl.allegroumk.allediet.service.model.Ingredient
import pl.allegroumk.allediet.service.model.Meal

class MongoMealsRepository(
    private val repository: SpringMongoMealsRepository
) : MealsRepository {

    override fun getAllMeals() = repository.findAll().map { it.toDomain() }

    override fun getMeal(id: String) = repository.findByIdOrNull(id)?.toDomain()

    override fun insertMeal(meal: Meal) = repository.save(meal.toDocument()).toDomain()

    override fun deleteMeal(id: String) = repository.deleteById(id)

    override fun updateMeal(mealToUpdate: Meal) = repository.save(mealToUpdate.toDocument()).toDomain()

    //override fun getMealsWithCaloriesBetween(minCalories: Int, maxCalories: Int) =
    //    repository.findByCaloriesBetween(minCalories, maxCalories).map { it.toDomain() }
    //
    //override fun getAllMealsSortedByNameDescending() = repository.findAllByOrderByCaloriesAsc().map { it.toDomain() }
    //
    //override fun getMealsWithMoreIngredientsThan(count: Int) =
    //    repository.findMealsWithMoreIngredientsThan(count).map { it.toDomain() }
    
    private fun MealMongo.toDomain() =
        Meal(
            id,
            name,
            calories,
            ingredients.map { Ingredient(it.name, it.calories) },
            createdAt,
            updatedAt
        )

    private fun Meal.toDocument() =
        MealMongo(
            id,
            name,
            calories,
            ingredients.map { IngredientMongo(it.name, it.calories) },
            createdAt,
            updatedAt
        )
}