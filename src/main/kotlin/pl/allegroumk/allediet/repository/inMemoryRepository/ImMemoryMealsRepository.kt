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

    override fun updateMeal(mealToUpdate: Meal) {
            deleteMeal(mealToUpdate.id)
            meals.add(mealToUpdate)
    }

    override fun deleteMeal(id: String) {
        meals.removeIf { it.id == id }
    }
}