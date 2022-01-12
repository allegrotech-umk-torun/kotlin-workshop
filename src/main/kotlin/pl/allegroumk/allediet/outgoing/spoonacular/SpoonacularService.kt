package pl.allegroumk.allediet.outgoing.spoonacular

import com.github.benmanes.caffeine.cache.LoadingCache
import org.springframework.stereotype.Component
import pl.allegroumk.allediet.outgoing.spoonacular.model.SpoonacularIngredientInformation
import pl.allegroumk.allediet.outgoing.spoonacular.model.SpoonacularNutrition
import pl.allegroumk.allediet.service.model.ExternalIngredient
import pl.allegroumk.allediet.service.model.IngredientInformation
import pl.allegroumk.allediet.service.model.IngredientsList

@Component
class SpoonacularService(
    private val spoonacularClient: SpoonacularClient,
    private val ingredientsInformationCache: LoadingCache<Long, SpoonacularIngredientInformation>
) {

    fun getIngredientsByName(name: String): IngredientsList {
        return spoonacularClient.findIngredientsByName(name)
            .results
            .map { ExternalIngredient(id = it.id, name = it.name) }
            .let { IngredientsList(it) }
    }

    fun getIngredientDetails(id: Long): IngredientInformation? {
        return ingredientsInformationCache.get(id)
            ?.let {
                IngredientInformation(
                    id = it.id,
                    name = it.name,
                    amount = it.amount,
                    unit = it.unit,
                    possibleUnits = it.possibleUnits,
                    calories = getCalories(it.nutrition)
                )
            }
    }

    private fun getCalories(nutrition: SpoonacularNutrition): Float {
        return nutrition.nutrients.first { it.name == "Calories" }.let { it.amount }
    }

}