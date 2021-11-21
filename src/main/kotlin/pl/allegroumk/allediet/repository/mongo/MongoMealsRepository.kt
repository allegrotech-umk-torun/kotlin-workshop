package pl.allegroumk.allediet.repository.mongo

import org.bson.Document
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction.ASC
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.Size.lengthOfArray
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import pl.allegroumk.allediet.repository.MealsRepository
import pl.allegroumk.allediet.service.model.Ingredient
import pl.allegroumk.allediet.service.model.Meal
import pl.allegroumk.allediet.service.model.MealToUpdate
import pl.allegroumk.allediet.service.model.MealsSummary
import java.time.LocalDateTime

class MongoMealsRepository(
    private val template: MongoTemplate
) : MealsRepository {

    override fun getAllMeals() = template.findAll(MongoMeal::class.java).map { it.toDomain() }

    override fun getMeal(id: String) = template.findById(id, MongoMeal::class.java)?.toDomain()

    override fun insertMeal(meal: Meal) = template.save(meal.toDocument()).toDomain()

    override fun deleteMeal(id: String) {
        val query = Query().addCriteria(Criteria.where("id").`is`(id))
        template.remove(query, MongoMeal::class.java)
    }

    override fun updateMeal(mealToUpdate: Meal) {
        template.save(mealToUpdate.toDocument()).toDomain()
    }

    override fun getAllMealsSortedByCaloriesAscending(): Iterable<Meal> {
        val query = Query().with(Sort.by(ASC, "calories"))
        return template.find(query, MongoMeal::class.java).map { it.toDomain() }
    }

    override fun getMealsWithCaloriesBetween(minCalories: Int, maxCalories: Int): Iterable<Meal> {
        val query = Query().addCriteria(Criteria.where("calories").gt(minCalories).lt(maxCalories))
        return template.find(query, MongoMeal::class.java).map { it.toDomain() }
    }

    override fun getMealsWithMoreIngredientsThan(count: Int): Iterable<Meal> {
        val query = Query().addCriteria(Criteria.where("ingredients.$count").exists(true))
        return template.find(query, MongoMeal::class.java).map { it.toDomain() }
    }

    override fun getMealsByNames(names: List<String>): Iterable<Meal> {
        val query = Query().addCriteria(Criteria.where("name").`in`(names))
        return template.find(query, MongoMeal::class.java).map { it.toDomain() }
    }

    override fun deleteMealsByName(name: String) {
        TODO("Not yet implemented")
    }

    override fun findAndModify(mealToUpdate: MealToUpdate): Meal? {
        val query = Query()
            .addCriteria(Criteria.where("id").`is`(mealToUpdate.id))
        val update = Update()
            .push("ingredients").each(mealToUpdate.ingredients)
            .inc("calories", mealToUpdate.ingredients.sumOf { it.calories })
            .set("updatedAt", LocalDateTime.now())
        val options = FindAndModifyOptions.options()
            .returnNew(true)
        return template.findAndModify(query, update, options, MongoMeal::class.java)?.toDomain()
    }

    override fun getMealsSummary(): MealsSummary? {
        val project = project(MongoMeal::class.java)
            .and("_id").`as`("mealId")
            .and(lengthOfArray("ingredients")).`as`("ingredientsCount")

        val sort = sort(DESC, "ingredientsCount")

        val group = group()
            .first("mealId").`as`("maxMealId")
            .first("ingredientsCount").`as`("maxIngredientsCount")
            .last("mealId").`as`("minMealId")
            .last("ingredientsCount").`as`("minIngredientsCount")

        val aggregation = newAggregation(project, sort, group)

        val result = template.aggregate(aggregation, MongoMeal::class.java, Document::class.java)

        return result.uniqueMappedResult?.toDomain()
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

    private fun Document.toDomain() =
        MealsSummary(
            getString("maxMealId"),
            getString("minMealId"),
            getInteger("maxIngredientsCount"),
            getInteger("minIngredientsCount")
        )
}