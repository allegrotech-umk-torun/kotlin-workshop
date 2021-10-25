package pl.allegroumk.allediet.repository

import org.springframework.stereotype.Component
import pl.allegroumk.allediet.api.model.InputIngredient
import pl.allegroumk.allediet.api.model.InputMeal
import javax.annotation.PostConstruct

@Component
class FeedRepository(
    val mealsRepository: MealsRepository
) {

    @PostConstruct
    fun feedRepository() {
        mealsRepository.insertMeal(InputMeal("breakfast", 300, listOf(InputIngredient("platki", 250), InputIngredient("kawa", 50))))
        mealsRepository.insertMeal(InputMeal("lunch", 400, listOf(InputIngredient("omlet", 500))))
        mealsRepository.insertMeal(InputMeal("dinner", 700, listOf(InputIngredient("schabowy", 400), InputIngredient("ziemniaki", 300))))
        mealsRepository.insertMeal(InputMeal("supper", 300, listOf(InputIngredient("kanapka", 300))))
    }
}