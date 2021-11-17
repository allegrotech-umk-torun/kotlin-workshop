package pl.allegroumk.allediet.repository.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface SpringMongoMealsRepository : MongoRepository<MealMongo, String> {

    fun findByCaloriesBetween(minCalories: Int, maxCalories: Int): Iterable<MealMongo>

    fun findAllByOrderByCaloriesAsc(): Iterable<MealMongo>

    @Query("{\$expr: {\$gte: [{\$size: \"\$ingredients\"}, ?0]}}")
    fun findMealsWithMoreIngredientsThan(ingredientsCount: Int): Iterable<MealMongo>

}