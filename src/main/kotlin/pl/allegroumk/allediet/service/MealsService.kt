package pl.allegroumk.allediet.service

import org.springframework.stereotype.Component
import pl.allegroumk.allediet.api.model.InputMeal
import pl.allegroumk.allediet.repository.MealsRepository

@Component
class MealsService(
    val mealsRepository: MealsRepository
) {

    fun getAllMeals() = mealsRepository.getAllMeals()
    fun insertMeal(inputMeal: InputMeal) = mealsRepository.insertMeal(inputMeal)
}