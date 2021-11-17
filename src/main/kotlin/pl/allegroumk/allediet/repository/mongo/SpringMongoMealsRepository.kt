package pl.allegroumk.allediet.repository.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface SpringMongoMealsRepository : MongoRepository<MongoMeal, String> {

    fun findByCaloriesBetween(minCalories: Int, maxCalories: Int): Iterable<MongoMeal>

    fun findAllByOrderByCaloriesAsc(): Iterable<MongoMeal>

    @Query("{\$expr: {\$gt: [{\$size: \"\$ingredients\"}, ?0]}}")
    fun findMealsWithMoreIngredientsThan(ingredientsCount: Int): Iterable<MongoMeal>

}