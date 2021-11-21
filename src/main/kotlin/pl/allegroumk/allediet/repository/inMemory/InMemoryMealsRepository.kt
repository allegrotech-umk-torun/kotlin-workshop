package pl.allegroumk.allediet.repository.inMemory

import pl.allegroumk.allediet.repository.MealsRepository
import pl.allegroumk.allediet.service.model.Ingredient
import pl.allegroumk.allediet.service.model.Meal

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

    private fun InMemoryMeal.toDomain(): Meal {
        return Meal(id, name, calories, listOf(Ingredient(name, calories)), createdAt, updatedAt)
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