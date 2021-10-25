package pl.allegroumk.allediet.repository.inMemoryRepository

import org.springframework.stereotype.Component
import pl.allegroumk.allediet.repository.MealsRepository
import pl.allegroumk.allediet.service.model.Ingredient
import pl.allegroumk.allediet.service.model.Meal
import java.time.LocalDateTime
import kotlin.collections.HashSet

@Component
class ImMemoryMealsRepository : MealsRepository {
    private val meals: MutableSet<Meal> = HashSet()

    override fun getAllMeals() = meals

    override fun getMeal(id: String) = meals.firstOrNull { it.id == id }

    override fun insertMeal(meal: Meal): Meal {
        meals.add(meal)
        return meal
    }

    override fun addIngredientsToMeal(idToUpdate: String, ingredientsToAdd: List<Ingredient>): Meal? {
        val mealToUpdate = getMeal(idToUpdate)
        val newMeal = mealToUpdate?.let {
                Meal(
                    id = it.id,
                    name = it.name,
                    ingredients = it.ingredients + ingredientsToAdd.map { Ingredient(it.name, it.calories) },
                    createdAt = it.createdAt,
                    updatedAt = LocalDateTime.now()
                )
        }
        if (newMeal != null) {
            deleteMeal(idToUpdate)
            meals.add(newMeal)
        }
        return newMeal
    }

    override fun deleteMeal(id: String) {
        meals.removeIf { it.id == id }
    }
}